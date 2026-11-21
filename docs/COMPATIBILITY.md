# Compatibility policy / سياسة التوافق

## Target

- Android package: `com.instagram.android`
- Instagram version: `456.0.0.53.78`
- Known upstream HushGram version code for ARM64: `386214574`
- Other HushGram metadata version codes: `ARMEABI_V7A=386214562`, `X86=386214575`

## Evidence levels

1. **Upstream-declared-only** — upstream metadata names the target version. Recorded in `patches/catalog.json`; not an independent guarantee.
2. **Source-reviewed** — patch implementation, fingerprints, integrations, dependencies and extension code reviewed against the target APK metadata.
3. **Build-verified** — reproducible MPP build succeeds with the patch and required extension dependencies.
4. **Runtime-verified** — patched app installs and the feature is exercised on a test device/account with results recorded.
5. **Rejected/deferred** — incompatible, unsafe, duplicate, unmaintained, unlicensed or not yet testable; reason must be recorded.

A patch cannot be described as fully compatible until at least Build-verified; runtime-sensitive behavior should reach Runtime-verified before release.

## Porting rules

- Do not bulk-change version strings or fingerprints to force acceptance.
- Rebase patch fingerprints and bytecode/smali targets against the exact 456 APK.
- Review method signatures, resource IDs, strings, protobuf/network models, extension APIs and dependencies.
- Preserve patch dependencies and extension resources; a patch must not be copied without its runtime implementation.
- Test interactions between overlapping patches and settings.
- Deduplicate by behavior and implementation, not name alone.
- Preserve original HushGram functionality unless a documented conflict or license issue requires otherwise.

## Release gate

No release is produced until the source tree builds the MPP, the catalog validator passes, all included patches have evidence records, required NOTICE/license materials are included, and the resulting artifact is inspected. CI success must be linked to the exact commit and artifact SHA-256.

## Reuse of HushGram's 456 baseline

The pinned HushGram source revision `da56d39d7c001bf052bcb7951e3051224bd39928` already declares Instagram `456.0.0.53.78` in `patches/src/main/kotlin/app/morphe/patches/shared/compat/AppCompatibilities.kt`, with ARM64 build code `386214574`, ARMv7 `386214562` and x86 `386214575`, and the two accepted Instagram/Meta signing-certificate digests. Its Gradle version is `0.0.21`.

IUP therefore does not need the user to upload the APK to begin source integration. We will inherit this compatibility declaration and HushGram's source/test/build system as the baseline. Do not downgrade the target to an older HushGram release or change its version metadata by hand.

An APK/DEX fixture is only a separate evidence input for independently re-running patch application and fingerprint checks. HushGram's own declared target and source-level fixture tests are the initial baseline evidence; IUP can proceed with structural import, Kotlin/Java integration, catalog generation, compile and fixture-independent tests without asking the user for a file.

**Evidence distinction:** inherited upstream target metadata is not the same as a new independent IUP run against every extra-source patch. Any imported Piko/Brosssh/Oxbow feature must still be source-adapted, compile-tested and clearly marked as not independently 456-verified until the relevant patch-application tests pass.