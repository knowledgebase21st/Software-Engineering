/*Author: Sanjay Ghosh                                            
 * This is an example to show how RSA works with public key/private key 
 * and how to encrypt decrypt
 */

import java.math.BigInteger;
import java.util.Scanner;

public class RSAEncryptDecrypt {

    // Generate RSA keys
    static class RSAKeyPair {
        BigInteger n; //multiplication of 2 prime numbers p and q
        BigInteger e; //public key exponent
        BigInteger d; //private key exponent

        RSAKeyPair(BigInteger p, BigInteger q) {
            n = p.multiply(q);

	    // Getting the totient of p x q
            BigInteger phi = (p.subtract(BigInteger.ONE))
                    .multiply(q.subtract(BigInteger.ONE));

            e = BigInteger.valueOf(10);
            while ((!e.gcd(phi).equals(BigInteger.ONE)) && (e.compareTo(phi)) <0) {
                e = e.add(BigInteger.ONE);

            }
	    //Here satisfying e*d mod (phi) = 1
            d = e.modInverse(phi);
	}
    }

    // Encrypt a single integer
    // c = m tothepower e mod (n)
    public static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    // Decrypt a single integer
    // m = c tothepower e mod (n)
    public static BigInteger decrypt(BigInteger cipher, BigInteger d, BigInteger n) {
        return cipher.modPow(d, n);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter prime p: ");
        BigInteger p = new BigInteger(scanner.nextLine());

        System.out.print("Enter prime q: ");
        BigInteger q = new BigInteger(scanner.nextLine());

        // Basic prime check
        if (!p.isProbablePrime(2) || !q.isProbablePrime(2)) {
            System.out.println("Both numbers must be prime.");
            return;
        }

        RSAKeyPair keyPair = new RSAKeyPair(p, q);

        System.out.println("\nPublic Key (e, n):");
        System.out.println("e = " + keyPair.e);
        System.out.println("n = " + keyPair.n);

        System.out.println("\nPrivate Key (d, n):");
        System.out.println("d = " + keyPair.d);

        System.out.print("\nEnter message to encrypt: ");
        String message = scanner.nextLine();

        System.out.println("\nEncrypted values:");

        BigInteger[] encrypted = new BigInteger[message.length()];

        for (int i = 0; i < message.length(); i++) {
            BigInteger m = BigInteger.valueOf((int) message.charAt(i));
            encrypted[i] = encrypt(m, keyPair.e, keyPair.n);
            System.out.print(encrypted[i] + " ");
        }

        System.out.println("\n\nDecrypted message:");

        StringBuilder decrypted = new StringBuilder();

        for (BigInteger cipher : encrypted) {
            BigInteger m = decrypt(cipher, keyPair.d, keyPair.n);
            decrypted.append((char) m.intValue());
        }

        System.out.println(decrypted.toString());

        scanner.close();
    }
}
