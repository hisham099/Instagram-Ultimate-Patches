# Upstream audit — 2026-11-20

This audit pins each repository to an immutable commit. Counts are from machine-readable patch manifests where available, not estimates from screenshots or old notes.

## Verified sources

| Source | Commit | License | Manifest / target evidence | Decision |
|---|---|---|---|---|
| [HushGram](https://github.com/SysAdminDoc/HushGram) | `da56d39d7c001bf052bcb7951e3051224bd39928` | GPL-3.0 | `patches-list.json` v0.0.21: **194 records**, 190 enabled by upstream metadata and 4 disabled; all 194 declare Instagram 456.0.0.53.78. | Primary baseline; cataloged, not yet imported as buildable source. |
| [Piko](https://github.com/crimera/piko) | `297519632c897d19e9f0df0c39e5b315d1a7390b` | GPL-3.0 | v3.11.1 manifest: 66 Instagram entries, all target 452.1.0.53.64. | Candidate for selective porting only. |
| [Brosssh Morphe patches](https://github.com/brosssh/morphe-patches) | `de158a4ebfc5376a71058b2c012a9895e46cdb14` | GPL-3.0 | v2.8.2 manifest: 18 Instagram entries, all target 439.0.0.37.89. | Candidate for selective porting only; old target. |
| [Oxbow](https://github.com/aic0d3r/oxbow) | `957e6a580fdaf4dd67e3ae8ee5384e223c85e94` | GPL-3.0 | Bundle 1.3.3 explicitly targets 455.0.0.51.72 and says all 19 patches apply to that version. | Candidate only; not compatible as published with target 456. |
| [FeurStagram](https://github.com/jean-voila/FeurStagram) | `14e8973b5224fcfc10590485ff99087f4d547f6e` | GPL-3.0 | Standalone Instagram-derived project with its own extension/patches; Oxbow is a related fork. Not a Morphe MPP patch manifest in the inspected root. | Inspect for unique implementation ideas; do not treat as directly importable MPP patches. |
| [Morphe patches](https://github.com/MorpheApp/morphe-patches) | `08d27cf03b985aaa0abe225a439ca084af82f2f8` | GPL-3.0 | Current main generated patch list has YouTube Music, Reddit and YouTube bundles; no Instagram patch module in the inspected tree. | Framework and lineage reference, not a current additional Instagram source in this snapshot. |

## HushGram count discrepancy — open issue

HushGram's README describes v0.0.21 as containing **186 patches**, while the current machine-readable `patches-list.json` at the pinned commit contains **194 records** (190 enabled by default, 4 disabled). The catalog preserves all 194 manifest records and their upstream flags. This project does not silently rewrite the count or discard eight entries. Reconcile the generated README, manifest generation rules and actual Gradle patch declarations before selecting the final build set.

## License and attribution controls

HushGram, Piko, Brosssh, Oxbow, FeurStagram and Morphe declare GPL-3.0 at the repository level. This is not a substitute for checking individual files and carried dependencies. HushGram's current `NOTICE` explicitly requires preservation in source distributions and derivative works, and records a chain of upstream provenance and Morphe trademark/name restrictions. Any derivative must keep the relevant license and notices, attribute each imported patch, and use distinct branding that does not imply Morphe authorship or endorsement.

## Evidence limits

- A manifest target declaration is not proof that the patch applies cleanly to every APK build variant.
- Piko and Brosssh are not accepted for 456 merely because they share Morphe APIs with HushGram.
- Oxbow's 455 bundle is not a 456 compatibility claim.
- FeurStagram's app/extension code is not a plug-in MPP source without a port.
- No source code has yet been merged into this repository, and no MPP artifact has been built.
