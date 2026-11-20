# Upstream audit — 2026-11-20

Pinned source snapshots; patch counts are derived from each source's machine-readable manifest.

| Source | Commit | License | Manifest evidence | Decision |
|---|---|---|---|---|
| [HushGram](https://github.com/SysAdminDoc/HushGram) | `da56d39d7c001bf052bcb7951e3051224bd39928` | GPL-3.0 | v0.0.21 `patches-list.json`: 194 Instagram records (190 enabled, 4 disabled); all declare 456.0.0.53.78. README says 186. | Primary baseline; 194 records normalized in `patches/catalog.json`. Source implementation still not imported. |
| [Piko](https://github.com/crimera/piko) | `297519632c897d19e9f0df0c39e5b315d1a7390b` | GPL-3.0 | v3.11.1: 66 Instagram records, all declare 452.1.0.53.64. | Candidate-only; port individually. |
| [Brosssh](https://github.com/brosssh/morphe-patches) | `de158a4ebfc5376a71058b2c012a9895e46cdb14` | GPL-3.0 | v2.8.2: 18 Instagram records, all declare 439.0.0.37.89. | Candidate-only; port individually. |
| [Oxbow](https://github.com/aic0d3r/oxbow) | `957e6a580fdaf4dd67e3ae8ee5384e223c85e94` | GPL-3.0 | v1.3.3 `patches-list.json`: 21 Instagram records target 455.0.0.51.72; `patches-bundle.json` release description says all 19 patches apply. | Candidate-only; manifest/release count mismatch (21 vs 19) and old target must be reconciled. |
| [FeurStagram](https://github.com/jean-voila/FeurStagram) | `14e8973b5224fcfc10590485ff99087f4d547f6e` | GPL-3.0 | Standalone Instagram-derived project; related lineage to Oxbow; not a direct Morphe MPP manifest. | Inspect unique implementation ideas; port required. |
| [Morphe patches](https://github.com/MorpheApp/morphe-patches) | `08d27cf03b985aaa0abe225a439ca084af82f2f8` | GPL-3.0 | Main snapshot has YouTube Music, Reddit and YouTube bundles; no Instagram patch module found in inspected tree. | Framework/lineage reference, not current additional Instagram source. |

## Manifest discrepancies

- HushGram README: 186 patches; patch-list manifest: 194 records (190 enabled, 4 disabled). All 194 manifest records declare 456.0.0.53.78. This project keeps all records pending reconciliation with Gradle declarations and generated README.
- Oxbow patch-list manifest: 21 Instagram records; release bundle description: 19 patches. All listed records declare 455.0.0.51.72. Keep all 21 in candidate inventory but do not infer bundle inclusion.

## Licensing

All six repositories above declare GPL-3.0 at repository level. Check file-level provenance and carried dependencies before importing. HushGram NOTICE explicitly requires preservation in source distributions and derivative works and includes upstream attribution and Morphe name/trademark restrictions. HushGram's LICENSE and NOTICE are copied verbatim to `third_party/hushgram/`; preserve them and applicable upstream notices for any derivative distribution.

## Evidence limits

A manifest declaration does not prove patch applicability to all APK variants. Piko/Brosssh/Oxbow are not accepted for 456 merely because they share Morphe APIs. FeurStagram is not directly importable as an MPP patch source. No patch source has yet been integrated into this repository, and no MPP artifact has been built.
