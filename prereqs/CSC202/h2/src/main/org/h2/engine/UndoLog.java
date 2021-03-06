/*
 * Copyright 2004-2010 H2 Group. Multiple-Licensed under the H2 License,
 * Version 1.0, and under the Eclipse Public License, Version 1.0
 * (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.engine;

import java.util.ArrayList;
import org.h2.constant.SysProperties;
import org.h2.message.DbException;
import org.h2.store.Data;
import org.h2.store.FileStore;
import org.h2.util.New;

/**
 * Each session keeps a undo log if rollback is required.
 */
public class UndoLog {
    private Database database;
    // TODO undo log entry: a chain would probably be faster
    //  and use less memory than an array
    private ArrayList<UndoLogRecord> records = New.arrayList();
    private FileStore file;
    private Data rowBuff;
    private int memoryUndo;

    /**
     * Create a new undo log for the given session.
     *
     * @param session the session
     */
    public UndoLog(Session session) {
        this.database = session.getDatabase();
    }

    /**
     * Get the number of active rows in this undo log.
     *
     * @return the number of rows
     */
    public int size() {
        if (SysProperties.CHECK && memoryUndo > records.size()) {
            DbException.throwInternalError();
        }
        return records.size();
    }

    /**
     * Clear the undo log. This method is called after the transaction is
     * committed.
     */
    public void clear() {
        records.clear();
        memoryUndo = 0;
        if (file != null) {
            file.closeAndDeleteSilently();
            file = null;
            rowBuff = null;
        }
    }

    /**
     * Get the last record and remove it from the list of operations.
     *
     * @return the last record
     */
    public UndoLogRecord getLast() {
        int i = records.size() - 1;
        UndoLogRecord entry = records.get(i);
        if (entry.isStored()) {
            int start = Math.max(0, i - database.getMaxMemoryUndo() / 2);
            UndoLogRecord first = null;
            for (int j = start; j <= i; j++) {
                UndoLogRecord e = records.get(j);
                if (e.isStored()) {
                    e.load(rowBuff, file);
                    memoryUndo++;
                    if (first == null) {
                        first = e;
                    }
                }
            }
            for (int k = 0; k < i; k++) {
                UndoLogRecord e = records.get(k);
                e.invalidatePos();
            }
            first.seek(file);
        }
        return entry;
    }

    /**
     * Remove the last record from the list of operations.
     *
     * @param trimToSize if the undo array should shrink to conserve memory
     */
    public void removeLast(boolean trimToSize) {
        int i = records.size() - 1;
        UndoLogRecord r = records.remove(i);
        if (!r.isStored()) {
            memoryUndo--;
        }
        if (trimToSize && i > 1024 && (i & 1023) == 0) {
            records.trimToSize();
        }
    }

    /**
     * Append an undo log entry to the log.
     *
     * @param entry the entry
     */
    public void add(UndoLogRecord entry) {
        records.add(entry);
        if (!entry.isStored()) {
            memoryUndo++;
        }
        if (memoryUndo > database.getMaxMemoryUndo() && database.isPersistent() && !database.isMultiVersion()) {
            if (file == null) {
                String fileName = database.createTempFile();
                file = database.openFile(fileName, "rw", false);
                file.seek(FileStore.HEADER_LENGTH);
                rowBuff = Data.create(database, SysProperties.PAGE_SIZE);
                Data buff = rowBuff;
                for (int i = 0; i < records.size(); i++) {
                    UndoLogRecord r = records.get(i);
                    saveIfPossible(r, buff);
                }
            } else {
                saveIfPossible(entry, rowBuff);
            }
            file.autoDelete();
        }
    }

    private void saveIfPossible(UndoLogRecord r, Data buff) {
        if (!r.isStored() && r.canStore()) {
            r.save(buff, file);
            memoryUndo--;
        }
    }

}
