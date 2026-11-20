# Upstream sources and review register / سجل المصادر

Audit date: 2026-11-20. See [the pinned audit](UPSTREAM-AUDIT-2026-11-20.md) for immutable commits and evidence. See [source-candidates.json](../patches/source-candidates.json) for the normalized Piko/Brosssh/Oxbow candidate inventory.

| Source | Current evidence | Decision |
|---|---|---|
| [HushGram](https://github.com/SysAdminDoc/HushGram) | v0.0.21; manifest has 194 Instagram records, 190 upstream-enabled and 4 disabled; all declare 456.0.0.53.78. README says 186, so count reconciliation remains open. | Primary baseline. Catalog metadata imported; source implementation not yet imported. Preserve LICENSE and NOTICE. |
| [Piko](https://github.com/crimera/piko) | v3.11.1; 66 Instagram entries, all declare 452.1.0.53.64. | Candidate for per-patch porting only. |
| [Brosssh](https://github.com/brosssh/morphe-patches) | v2.8.2; 18 Instagram entries, all declare 439.0.0.37.89. | Candidate for per-patch porting only. |
| [Oxbow](https://github.com/aic0d3r/oxbow) | Manifest lists 21 Instagram entries for 455.0.0.51.72; release description says 19. | Candidate only; old target. |
| [FeurStagram](https://github.com/jean-voila/FeurStagram) | Standalone GPL-3.0 Instagram-derived source; related to Oxbow; not a directly compatible MPP bundle. | Inspect for unique implementation ideas; port required. |
| [Morphe patches](https://github.com/MorpheApp/morphe-patches) | Current main snapshot includes YouTube Music, Reddit and YouTube; no Instagram patch module found. | Framework and lineage reference, not a current additional Instagram patch source in this snapshot. |

## Count discrepancy

HushGram's README says 186 patches, while the machine-readable `patches-list.json` at the pinned source commit has 194 records (190 enabled by upstream metadata and 4 disabled). This repository catalogs all 194 records without deleting entries or pretending the discrepancy is resolved. The Gradle declarations and generated README must be reconciled before selecting the final build set.

## Licensing and attribution

HushGram, Piko, Brosssh, Oxbow, FeurStagram and Morphe declare GPL-3.0 at repository level. Individual files and carried dependencies still require review. HushGram's NOTICE requires preservation in source distributions and derivative works and records upstream provenance and Morphe name/trademark restrictions. Retain applicable LICENSE/NOTICE files, attribution, per-file provenance and third-party license texts. Use distinct project branding; do not imply Morphe authorship or endorsement.

## Inclusion evidence required

1. Exact repository and immutable commit SHA.
2. Original patch identifier and source file paths.
3. License, NOTICE and provenance obligations.
4. Target APK fingerprints, methods, resources and dependency assumptions.
5. Duplicate/conflict analysis.
6. Adaptation commit and rationale, if changed.
7. Successful build against the pinned target.
8. Runtime test evidence for behavior-sensitive patches.
9. Arabic/English strings and RTL/LTR checks for user-facing settings.

A patch's upstream version declaration is not an independent compatibility guarantee.