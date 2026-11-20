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
