# JVault

JVault is a cross-platform and lightweight encrypted file container utility that offers the portability and flexibility
of securing data on any medium without admin privileges. By emulating a file explorer view of files and directories
stored in an encrypted container (a "vault"), users are able to add and delete files while transparent AES-256
encryption occurs in the background. Encrypted vaults can be stored locally or synchronized with any cloud storage
service. All aspects of the program are open source and client-side.

## Rationale
With the growing prevalence of cybersecurity threats that account for the majority of modern-day breaches of sensitive
data, the necessity of sufficiently secure encryption is essential. While transferring sensitive data over unencrypted
mediums, such as a USB flash drive, is convenient and hassle-free, it unfortunately enables and increases the risk of
unauthorized access to the data. Moreover, existing products that offer file encryption are too inflexible and time
consuming to operate or require admin privileges in exchange for seamless and transparent encryption. I hope to resolve
these limitations and encourage individuals to value data security.

## Features
- Vault can be stored anywhere as a directory
- Encryption key is derived from the password using PBKDF2 and hashed using HMAC SHA-256
- Cryptographically secure 200,000 rounds of iteration, and 16 bytes for salts and IVs generated using Java
    SecureRandom
- AES-GCM algorithm with 256-bit key length and 128-bit tag length
- File contents, file names, and folder names are encrypted

## Deployment
For users who are concerned with protecting sensitive data from unauthorized access when transporting on unencrypted
mediums, JVault provides a lightweight implementation to efficiently encrypt and store files with minor adjustments to
productivity workflow. As an example, students who wish to share local files with a USB flash drive can create a JVault
encrypted container and add files using the utility, rather than directly copying onto the drive.