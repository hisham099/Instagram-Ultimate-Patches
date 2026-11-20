# Upstream audit snapshot — 2026-11-20

This is a source-metadata audit, not a completed code merge or a build certification by this repository.

## Revisions inspected

| Project | Default branch | Inspected commit/tree SHA | Current upstream metadata |
|---|---|---|---|
| HushGram | `main` | `da56d39d7c001bf052bcb7951e3051224bd39928` | Release metadata identifies v0.0.21; README says 186 patches; `patches-list.json` contains 194 records |
| Piko | `main` | `297519632c897d19e9f0df0c39e5b315d1a7390b` | `patches-list.json` v3.11.1 contains 140 total records, 66 Instagram-compatible records, all declaring Instagram 452.1.0.53.64 |
| Oxbow | `main` | `957e6a580fdaf4dd67e3ae8ee5384e223c85e94e` | Bundle 1.3.3 contains 19 patches for Instagram 455.0.0.51.72 |

## Key finding: HushGram is the current target baseline

The HushGram repository description, README, and patch manifest identify Instagram **456.0.0.53.78**. The current `patches-list.json` contains **194 patch records**, each declaring `com.instagram.android` target version `456.0.0.53.78`; 190 records have `use: true`. The README's release summary says 186 patches, so there is a metadata/count discrepancy that must be resolved before publishing our own final catalog. The manifest count is a record count, not proof that all 194 implementations build or work correctly.

HushGram is therefore the primary candidate baseline for this target. Its README explicitly advises using HushGram as the only Instagram source because mixing another source can stop patching. Do not combine separate Morphe sources blindly. The integration must first establish whether the selected implementation can be vendored/rebased into one source tree, with patch IDs, dependencies, and extension code reconciled.

## Other sources: not target-compatible as published

- **Piko v3.11.1:** 66 Instagram entries in its current manifest, all targeting 452.1.0.53.64. They are candidates for source-level review only; they must not be marked compatible with 456.0.0.53.78 without reworking fingerprints/hooks and testing.
- **Oxbow 1.3.3:** its release metadata and patch list target 455.0.0.51.72. The project describes itself as a FeurStagram fork. Its 19 patches are candidates for selective implementation review, not direct inclusion as a compatible bundle.
- **Brosssh and FeurStagram:** not yet audited in this snapshot. Their current commits, patch inventories, and license notices still need to be captured.

## Licensing and attribution gate

HushGram declares GPL-3.0. Its `NOTICE` documents inherited components and third-party attribution, including Piko/Morphe-related notices. It also contains a Morphe project-name restriction requiring derivative works to use distinct branding and permitting only accurate, secondary descriptive compatibility references. Any reuse must retain applicable license and notice material and must not present this project as an official Morphe product.

Piko and Oxbow metadata also identify GPL-3.0. This is not a substitute for checking each source file's provenance and the complete notice chain before importing code.

## Counts from current manifests

| Source | Manifest records | Instagram records | Declared target |
|---|---:|---:|---|
| HushGram v0.0.21 | 194 | 194 | 456.0.0.53.78 |
| Piko v3.11.1 | 140 | 66 | 452.1.0.53.64 |
| Oxbow 1.3.3 | 19 | 19 | 455.0.0.51.72 |

These counts describe manifest entries, not unique behaviors or independently verified working patches. Duplicate detection and implementation-level review are still required.

## Next technical gates

1. Capture Brosssh, FeurStagram, and official Morphe revisions and complete license/NOTICE chains.
2. Pin the exact HushGram/Morphe Gradle plugin, patcher, extension, and dependency versions.
3. Build the HushGram target baseline reproducibly before making modifications.
4. Normalize all source manifests and compare duplicate behavior, identifiers, dependencies, and settings keys.
5. Port only selected candidates into the single IUP source tree; adapt fingerprints and resource hooks for 456.0.0.53.78.
6. Add bilingual strings and verify settings persistence and RTL/LTR behavior.
7. Run CI build and tests; only then promote patch statuses or publish an MPP artifact.
