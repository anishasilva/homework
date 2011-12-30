
/**
 *
 * @author rayelward
 */
import javax.swing.*;

public class CoinToss {

    public static void main(String args[]) {

        String ans;
        do {
            int coinArray[] = new int[100];
            int headsCount = 0, tailsCount = 0;
            for (int i = 0; i < coinArray.length; i++) {
                coinArray[i] = (int) (Math.random() * 2) + 1;
                switch (coinArray[i]) {
                    case 1:
                        headsCount++;
                        break;
                    case 2:
                        tailsCount++;
                        break;
                }
            }

            JOptionPane.showMessageDialog(null, String.format("%s %d\n%s %d\n",
                    "Heads:", headsCount,
                    "Tails:", tailsCount));

            ans = JOptionPane.showInputDialog("Do you want to toss the coin 100 more times? ('yes' or 'no')?");

        } while (ans.equals("yes"));
        System.exit(0);
    }
}
