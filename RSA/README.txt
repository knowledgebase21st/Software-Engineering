This RSAEncryptDecrypt.java is an example how RSA works. 
It demonstrates how public key/private key exponent is created and
also how the encryption of each character of the message is done and
how to decrypt that too.

Compile the code : javac RSAEncryptDecrypt.java
Run              : java RSAEncryptDecrypt

It will prompt you 
integer 1
integer 2
Message you want to encrypt

As a result it will show the encrypted key and it will decrypt back to the original message
to prove that it works.

----------------Here is an example of the run----------------------
java RSAEncryptDecrypt
Enter prime p: 79
Enter prime q: 5333

Public Key (e, n):
e = 11
n = 421307

Private Key (d, n):
d = 151235

Enter message to encrypt: This is original Message

Encrypted values:
372606 392577 414799 32093 190593 414799 32093 190593 197387 295666 414799 276477 414799 182747 122934 244266 190593 178230 316798 32093 32093 122934 276477 316798

Decrypted message:
This is original Message
------------------------------------------------------------------
You can try different values and play with the copy of the code.
