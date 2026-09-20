# Chapter 7 – Password Hashing and Salting

This directory contains the source code for **Chapter 7 – Password Hashing and Salting** of the book:

**Practical Application Security**  
*A Software Engineer's Guide to Secure Development, Authentication, Cryptography, and AI-Assisted Security Engineering*

The examples demonstrate practical techniques for securely storing and verifying passwords, while also showing common insecure approaches and why they should be avoided.

## What This Chapter Covers

Chapter 7 explains:

- Why passwords should never be stored in plaintext
- Hashing vs. encryption
- Password-specific hashing
- Why fast general-purpose hashes are unsuitable for password storage
- Salting and unique salts
- Unsalted vs. salted password hashes
- Rainbow tables and offline password attacks
- bcrypt, scrypt, PBKDF2, and Argon2
- Work factors and computational cost
- Secure password verification
- Password-hash migration and upgrading
- Common password-storage mistakes
- Testing password-storage implementations

## Examples in This Directory

The examples are organized by concept rather than by individual section of the chapter.

### `plaintext-demo`

Demonstrates why storing passwords in plaintext is unsafe.

This is an intentionally insecure example for educational purposes only.

#### Build

From the `plaintext-demo` directory:

```bash
mvn clean package

Run
After a successful build:

java -cp target/classes com.clockwiseknowledge.security.PlaintextPasswordDemo



### `unsalted-vs-salted`

Demonstrates the difference between unsalted and salted password hashes.

The example shows how the same password can produce the same hash when no salt is used, while unique salts cause the stored password values to differ.

#### Build

From the `unsalted-vs-salted` directory:

```bash
mvn clean package

Run

After a successful build:

java -cp target/classes com.clockwiseknowledge.security.UnsaltedVsSaltedDemo

### `password-verification`

Demonstrates how a password should be verified against a stored password hash without recovering the original password.

The example uses `BCryptPasswordEncoder` from Spring Security Crypto.

#### Build

From the `password-verification` directory:

```bash
mvn clean package

Run

After a successful build:

mvn exec:java


### `password-algorithms`

Demonstrates four password-specific hashing algorithms using established Spring Security implementations:

- BCrypt
- SCrypt
- PBKDF2
- Argon2id

The example creates a password hash using each algorithm and then verifies both a correct and an incorrect password.

The program also measures password verification time to demonstrate the effect of password-hashing computational cost.

#### Build

From the `password-algorithms` directory:

```bash
mvn clean package

Run

After a successful build:

mvn exec:java

The program displays:

The encoded password produced by each algorithm.
Whether the correct password was successfully verified.
Whether the incorrect password was rejected.
The approximate verification time for each test.

Example output:

========================================
BCrypt
========================================
Encoded password:
$2a$10$...

Correct password verification: true
Correct verification time: ... ms
Incorrect password verification: false
Incorrect verification time: ... ms

The same type of information is displayed for SCrypt, PBKDF2, and Argon2id.

Important: The verification timings are illustrative and must not be interpreted as a security ranking of the algorithms. Password-hashing parameters should be benchmarked and tuned using hardware and workloads representative of the target production environment.


### `work-factor`

Demonstrates how the BCrypt work factor affects password verification time.

The example creates BCrypt password hashes using different work factors and measures the time required to verify the password.

#### Build

From the `work-factor` directory:

```bash
mvn clean package
Run

After a successful build:

mvn exec:java

The program displays:

The BCrypt work factor being tested.
Whether the password was successfully verified.
The approximate password verification time.

Example output:

BCrypt Work-Factor Demonstration
================================

Work factor: 10
Password verified: true
Verification time: ... ms

Work factor: 11
Password verified: true
Verification time: ... ms

Work factor: 12
Password verified: true
Verification time: ... ms

Note: Timings are specific to the current execution environment.

The example demonstrates that increasing the work factor increases the computational work required for password verification.

Important: The verification timings are specific to the execution environment and must not be interpreted as universal benchmarks or security rankings. Password-hashing parameters should be benchmarked and tuned using hardware and workloads representative of the target production environment.



### `password-migration`

Demonstrates how an application can migrate password hashes from a legacy password-hashing configuration to a newer password-hashing algorithm.

The example simulates the following migration:

```text
Legacy BCrypt Hash
        |
        v
User Login
        |
        v
Verify Supplied Password
        |
        | Successful
        v
Rehash Supplied Password
Using Argon2id
        |
        v
Replace Stored Password Hash
        |
        v
Verify Using New Hash

The example demonstrates an important migration principle:

Do not hash the old password hash with the new algorithm.

Instead, the application should:

Verify the password supplied by the user against the existing hash.
If authentication succeeds, use the supplied password to create a new hash using the current password-hashing algorithm.
Replace the legacy hash in the database with the new hash.
Use the new hash for subsequent authentication.
Build

From the password-migration directory:

mvn clean package
Run

After a successful build:

mvn exec:java

The program demonstrates:

Verification of a password using the legacy BCrypt hash.
Successful authentication.
Migration of the password to Argon2id.
Replacement of the simulated stored password hash.
Verification using the migrated Argon2id hash.

Example output:

PASSWORD MIGRATION DEMONSTRATION
================================

Legacy password-hashing algorithm: BCrypt
Stored legacy hash:
$2a$10$...

Legacy password verification: true

Password successfully migrated.
Current password-hashing algorithm: Argon2id
New stored hash:
$argon2id$...

Migrated password verification: true

The exact encoded password values will differ between executions because password-hashing algorithms use salts.

Important: Password migration should occur only after successful verification of the user's existing password. An application should never attempt to recover the original password from a legacy hash or simply hash the legacy hash with the new algorithm.

## Building All Examples from the Root

Chapter 7 uses a Maven multi-module project structure.

The `pom.xml` in the `chapter-7` directory is the parent and aggregator POM. It defines the common Java version, dependency versions, and Maven build configuration for the Chapter 7 examples.

The individual examples remain separate Maven modules so that each security concept can be built and studied independently.

### Build All Examples

From the `chapter-7` directory, run:

```bash
mvn clean package

This builds all Chapter 7 examples defined in the root pom.xml.

After a successful build, each example has its own target directory containing its compiled classes and JAR file.

Run an Individual Example

After building the examples from the root, an individual example can be run from its own directory.

For example:

cd password-verification
mvn exec:java

Other examples can be run as follows:

cd plaintext-demo
java -cp target/classes com.clockwiseknowledge.security.PlaintextPasswordDemo
cd unsalted-vs-salted
java -cp target/classes com.clockwiseknowledge.security.UnsaltedVsSaltedDemo
cd password-algorithms
mvn exec:java
cd work-factor
mvn exec:java
cd password-migration
mvn exec:java

The individual example directories also contain Build and Run instructions specific to that example.

Build an Individual Example

An individual example can also be built independently.

For example:

cd password-migration
mvn clean package

This is useful when working with or modifying only one example.


## Project Structure

chapter-7/
├── README.md
├── pom.xml
│
├── plaintext-demo/
├── unsalted-vs-salted/
├── password-verification/
├── password-algorithms/
├── work-factor/
└── password-migration/

## Prerequisites

The examples require:

- Java 17 or later
- Apache Maven 3.x

The examples are designed to be small and self-contained so that readers can build and experiment with them independently.

## Build the Chapter

From the `chapter-7` directory:

mvn clean package

Individual examples can also be built from their respective directories when they contain their own Maven configuration.

## Running the Examples

Each example contains instructions in its own directory or source code.

A typical workflow is:

Read
  ↓
Review the code
  ↓
Build
  ↓
Run
  ↓
Experiment
  ↓
Observe the result
  ↓
Identify the security weakness
  ↓
Apply the secure approach
  ↓
Test

## Important Security Notice

Some examples in this directory intentionally demonstrate insecure password-storage techniques.

These examples are included to help readers understand security weaknesses and recognize them during code review.

**Do not use the intentionally insecure implementations in production applications.**

Never use real passwords, production credentials, or real authentication data when experimenting with these examples.

## Source Code and Learning Approach

The examples are intended to complement the explanations in Chapter 7.

Rather than simply providing ready-made implementations, the examples encourage readers to:

1. Understand the security requirement.
2. Examine the implementation.
3. Identify potential weaknesses.
4. Run the example.
5. Experiment with different inputs and configurations.
6. Apply the secure approach.
7. Verify the behavior through testing.

## Related Chapter

These examples accompany:

**Chapter 7 – Password Hashing and Salting**

from *Practical Application Security*.

The book explains the security principles behind these examples in greater detail, including password hashing, salting, offline attacks, password-hashing algorithms, work factors, verification, migration, and secure Java implementation.

## Author

**Sanjay Ghosh**
