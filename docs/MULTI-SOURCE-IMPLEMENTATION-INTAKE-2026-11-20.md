# Multi-source implementation intake / تدقيق الشيفرات متعددة المصادر

Audit date: 2026-11-20. This is a source-tree inventory, not a compatibility certification.

## Confirmed implementation source exists

The pinned source trees were resolved through each commit's Git tree and recursively enumerated. These counts include only the source roots listed below; they are not counts of accepted or independently compatible patches.

| Source | Pinned commit | Source files found | Main implementation roots | Declared Instagram target |
|---|---|---:|---|---|
| Piko | `297519632c897d19e9f0df0c39e5b315d1a7390b` | 470 | `patches/src/main/kotlin`, `extensions/instagram/src/main/java` | 452.1.0.53.64 |
| Brosssh | `de158a4ebfc5376a71058b2c012a9895e46cdb14` | 29 | `patches/src/main/kotlin`, `extensions/instagram/src/main/java` | 439.0.0.37.89 |
| Oxbow | `957e6a580fdaf4dd67e3ae8ee5384e223c85e94` | 46 | `patches/src/main/kotlin`, `extensions/extension/src/main/java` | 455.0.0.51.72 |
| FeurStagram | `14e8973b5224fcfc10590485ff99087f4d547f6e` | 54 | `patches/src/main/kotlin`, `extensions/extension/src/main/java` | Standalone Instagram-derived project; inspect individually |

Piko has substantial Java extension implementation, not just patch metadata. Brosssh contains 18 Instagram manifest entries but only a small number of extension Java classes, so each patch's dependency on shared extension behavior must be traced. Oxbow and FeurStagram contain Kotlin patch implementations plus Java extension code and are related lineages; do not count them as independent features without code-level deduplication.

## Concrete patch families located

- **Brosssh:** distraction-free controls including Reels scrolling, story auto-flipping, autoplay, ads, Explore/feed, notes tray, suggested content and navigation; plus limit-feed-to-followed-profiles and miscellaneous clone/signature/expiry handling.
- **Oxbow:** clone, caption draft, debug bridge, color mode, distraction/feed filtering, downloads, Meta AI hiding, clean links, ghost mode, restart relay, settings entry point, signature check, icon/notes/popup/update behavior.
- **FeurStagram:** overlapping Oxbow-derived functionality plus grayscale, comments, Quoicouview assets/patches and extension helpers.
- **Piko:** broad Instagram patch and extension tree; 470 source files in the two inspected roots. The full candidate manifest remains 66 entries at its pinned audit commit.

## Integration decisions

1. Preserve source snapshots and provenance by immutable commit; do not overwrite HushGram's existing implementation.
2. Build a source-to-source mapping for every candidate: manifest ID -> Kotlin patch class -> Java extension classes/resources -> dependencies -> settings/localization -> target fingerprints.
3. Compare Oxbow against FeurStagram first because of shared lineage; compare both against HushGram and Piko to avoid duplicate features.
4. Prioritize standalone, low-coupling patches for first ports. Defer broad feed/network filtering and signature/clone behavior until dependencies and target hooks are understood.
5. Port against the exact Instagram 456.0.0.53.78 APK fingerprints; never change target version strings to force a patch to load.
6. Keep candidate, ported, build-verified and runtime-verified states separate.

## Current honest status

- HushGram baseline MPP build: successful, 194 patches verified in its bundle.
- Piko/Brosssh/Oxbow/FeurStagram implementation trees: located and enumerated at pinned commits.
- Extra-source code copied into the IUP build graph: **not yet done**.
- Additional-source ports verified against Instagram 456.0.0.53.78: **none yet**.
- Unified IUP MPP: **not yet built**.

This inventory is the intake map for the next code-import and porting work; it is not a claim that the extra patches already work.
