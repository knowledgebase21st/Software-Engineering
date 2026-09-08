================================================================================
                         CHAPTER 6 – AUTHENTICATION ATTACKS
================================================================================

This directory contains the companion source code for Chapter 6 – Authentication
Attacks of the book:

    Practical Application Security
    A Software Engineer's Guide to Authentication, Cryptography,
    and Secure Development

The examples demonstrate common authentication attacks and practical security
controls that can help reduce the risk of account compromise.

The examples are intentionally small and are designed for local educational
use only. Do not use them against systems, applications, or accounts without
explicit authorization.


TOPICS COVERED
--------------

    - Brute Force Attacks
    - Dictionary Attacks
    - Rainbow Tables
    - Password Spraying
    - Credential Stuffing
    - Authentication Attack Defenses
    - Authentication Security in Java Applications
    - Common Authentication Security Mistakes


REPOSITORY STRUCTURE
--------------------

    chapter-6/
    |
    +-- README.md
    +-- pom.xml
    |
    +-- demo/
    |   +-- setup.sh
    |   +-- cleanup.sh
    |
    +-- brute-force/
    |   +-- pom.xml
    |   +-- src/
    |       +-- main/
    |           +-- java/
    |
    +-- dictionary-attack/
    |   +-- pom.xml
    |   +-- src/
    |       +-- main/
    |           +-- java/
    |
    +-- rainbow-table/
    |   +-- pom.xml
    |   +-- src/
    |       +-- main/
    |           +-- java/
    |
    +-- password-spraying/
    |   +-- pom.xml
    |   +-- src/
    |       +-- main/
    |           +-- java/
    |
    +-- credential-stuffing/
        +-- pom.xml
        +-- src/
            +-- main/
                +-- java/


Each attack example is an independent Maven module managed by the parent
pom.xml.


REQUIREMENTS
------------

    - Java 17 or later
    - Maven 3.8 or later
    - Bash
    - Linux, macOS, or Windows with WSL2

The examples are designed to run locally and do not require paid services or
external authentication systems.


SETUP
-----

Clone the repository and navigate to the Chapter 6 directory:

    git clone https://github.com/knowledgebase21st/Software-Engineering.git

    cd Software-Engineering/books/security/chapter-6

Make the setup script executable if necessary:

    chmod +x demo/setup.sh

Run the setup script:

    ./demo/setup.sh

The setup script verifies that Java and Maven are available and prepares the
directories required by the Chapter 6 examples.


BUILD ALL EXAMPLES
------------------

From the chapter-6 directory:

    mvn clean package

The parent Maven project builds all five Chapter 6 modules:

    - brute-force
    - dictionary-attack
    - rainbow-table
    - password-spraying
    - credential-stuffing

Each module creates its own target directory containing the compiled classes.


RUN THE EXAMPLES
----------------

After a successful build, each example can be executed using its compiled
classes.


1. BRUTE FORCE
--------------

    cd brute-force

    java -cp target/classes \
        com.practicalapplicationsecurity.bruteforce.BruteForceDemo

The example systematically generates possible passwords and attempts them
against a single test account.


2. DICTIONARY ATTACK
--------------------

    cd dictionary-attack

    java -cp target/classes \
        com.practicalapplicationsecurity.dictionaryattack.DictionaryAttackDemo

The example uses a predefined list of likely passwords and attempts them
against a single test account.


3. RAINBOW TABLE
----------------

    cd rainbow-table

    java -cp target/classes \
        com.practicalapplicationsecurity.rainbowtable.RainbowTableDemo

The example demonstrates the concept of using precomputed password hashes
to look up a password when a password hash has been obtained.


4. PASSWORD SPRAYING
--------------------

    cd password-spraying

    java -cp target/classes \
        com.practicalapplicationsecurity.passwordspraying.PasswordSprayingDemo

The example demonstrates using a small set of common passwords against
multiple test accounts.


5. CREDENTIAL STUFFING
----------------------

    cd credential-stuffing

    java -cp target/classes \
        com.practicalapplicationsecurity.credentialstuffing.CredentialStuffingDemo

The example demonstrates how previously compromised username/password pairs
can be reused against a different target application.


UNDERSTANDING THE DIFFERENCES
-----------------------------

The five examples demonstrate different ways an attacker may select or reuse
credentials.

    Brute Force
    -----------
    One account
        |
        +-- Generate possible password combinations
        |
        +-- Try each password


    Dictionary Attack
    -----------------
    One account
        |
        +-- Use a predefined list of likely passwords
        |
        +-- Try each password


    Rainbow Table
    -------------
    Stolen password hash
        |
        +-- Look up hash in a precomputed table
        |
        +-- Identify matching password


    Password Spraying
    -----------------
    Multiple accounts
        |
        +-- Try one common password
        |
        +-- Move across multiple accounts
        |
        +-- Try another common password


    Credential Stuffing
    -------------------
    Previously compromised credentials
        |
        +-- Username + password pairs
        |
        +-- Try the same pairs against another service


EDUCATIONAL USE
---------------

All examples are provided for security education and local experimentation.

Use these examples only against systems, applications, and accounts that you
own or are explicitly authorized to test.

Do not use these examples against production systems, third-party services,
or accounts without permission.


CLEANUP
-------

After completing the demonstrations, return to the chapter-6 directory:

    cd ..

If you are currently inside one of the example directories, make sure you
return to the chapter-6 directory before running the cleanup script.

Run:

    ./demo/cleanup.sh

The cleanup script removes Maven target directories created during the builds.

It does not remove source code, POM files, README files, or other project
files.


RELATED CHAPTER
---------------

These examples accompany Chapter 6 – Authentication Attacks in:

    Practical Application Security
    A Software Engineer's Guide to Authentication, Cryptography,
    and Secure Development

The chapter explains common authentication attacks, their risks, defensive
strategies, Java implementation considerations, common developer mistakes,
and practical authentication security guidelines.


LICENSE
-------

The source code in this repository is provided for educational purposes as
part of the companion materials for Practical Application Security.

================================================================================
