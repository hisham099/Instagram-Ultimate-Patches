# Integration architecture / بنية الدمج

## Current integration boundary

The repository currently contains:
- A normalized catalog of HushGram's 194 Instagram manifest entries.
- A separate candidate inventory of 105 Piko, Brosssh and Oxbow manifest entries.
- Pinned source commits and preserved HushGram LICENSE/NOTICE.
- Catalog validation CI.

It does **not** yet contain the upstream Kotlin patch implementation or Java extension implementation. The metadata catalog is not itself an MPP package.

## Reproducible baseline build

`.github/workflows/upstream-baseline-build.yml` checks out HushGram at immutable commit
`da56d39d7c001bf052bcb7951e3051224bd39928` and invokes the upstream Gradle task
`:patches:buildAndroid`. The workflow uploads the upstream baseline MPP,
CycloneDX SBOM and SHA-256 checksum as a workflow artifact.

This is a **baseline build**, not the merged Instagram Ultimate Patches bundle. It exists to:
1. Prove that the pinned source revision can be built reproducibly in CI.
2. Preserve an inspectable MPP artifact and SBOM for the baseline.
3. Establish a known-good comparison point before importing or porting candidate patches.

The baseline artifact must not be renamed or published as an IUP release.

## Next integration gates

1. Complete the baseline workflow and inspect its artifact and Gradle logs.
2. Decide whether to vendor the complete HushGram source tree or maintain it as a pinned upstream subtree/submodule; keep the full source history/provenance and all required notices.
3. Port selected Piko/Brosssh/Oxbow patches one at a time, including Kotlin implementation, extension Java/resources, dependencies, settings, and localization.
4. Run upstream unit/patch tests and build against the exact Instagram 456.0.0.53.78 APK build(s) listed in the pinned upstream catalog.
5. Run patch interaction tests, verify the final MPP contents and SBOM, then publish a distinct IUP release only after all gates pass.

## Important distinction

A successful catalog validator only checks the JSON inventory. A successful baseline build proves the pinned HushGram project builds. Neither proves that any extra candidate patch has been ported or that a combined IUP package exists.


## Multi-source implementation intake (2026-11-20)

A recursive Git-tree audit has now located the actual Kotlin patch and Java extension source roots in Piko, Brosssh, Oxbow and FeurStagram. See [MULTI-SOURCE-IMPLEMENTATION-INTAKE-2026-11-20.md](MULTI-SOURCE-IMPLEMENTATION-INTAKE-2026-11-20.md) for pinned commits, source-file counts, feature families and the import order.

The extra sources are **not** yet copied into the IUP build graph. Their manifest records remain candidates until each patch's Kotlin implementation, extension classes/resources, dependencies, settings, localization and exact 456.0.0.53.78 fingerprints are mapped and ported. Oxbow and FeurStagram are related lineages and must be deduplicated by implementation, not added as two full feature sets.
