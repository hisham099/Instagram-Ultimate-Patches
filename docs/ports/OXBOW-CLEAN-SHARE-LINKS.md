# Port assessment: Oxbow — Clean share links

Audit date: 2026-11-20  
Decision: **candidate accepted for porting work; not yet ported or compatible-certified for Instagram 456.0.0.53.78.**

## Immutable provenance

- Upstream: https://github.com/aic0d3r/oxbow
- Commit: `957e6a580fdaf4dd67e3ae8ee5384e223c85e94`
- License declared by upstream repository: GPL-3.0 (retain upstream license and attribution when source is imported).
- Kotlin patch: `patches/src/main/kotlin/com/feurstagram/patches/privacy/CleanLinksPatch.kt`
- Java extension: `extensions/extension/src/main/java/com/feurstagram/extension/ShareLinks.java`
- Runtime setting provider: `extensions/extension/src/main/java/com/feurstagram/extension/Config.java`, `Config.isCleanLinks()`
- Existing UI setting: `extensions/extension/src/main/java/com/feurstagram/extension/Settings.java`, row key `clean_links`.

## Behavior and implementation map

1. Redirects calls to:
   - `ClipData.newPlainText(CharSequence, CharSequence)`
   - `android.text.ClipboardManager.setText(CharSequence)`
   - `android.content.ClipboardManager.setText(CharSequence)`
   into the extension cleaner.
2. Inserts a cleaner call at the start of the share-intent Bundle builder identified by strings `android.intent.extra.EXCLUDE_COMPONENTS` and `image/jpeg`.
3. Inserts a cleaner call in the video-share branch identified by `android.intent.extra.TEXT`, `media_id`, and `ranking_token`.
4. Only Instagram URLs on `instagram.com`, `instagr.am`, and `ig.me` are modified. The query allowlist preserves `img_index`, `story_media_id`, `comment_id`, and `hl`; other query parameters are stripped. URL fragments are retained.
5. The runtime switch defaults to enabled via `Config.isCleanLinks() -> getBlocked("clean_links", true)`. The Oxbow settings UI labels it “Clean shared links”.

## Compatibility evidence and gap

Oxbow's own `OXBOW.md` records:
- End-to-end device test on Instagram 454 for Copy link and share-sheet paste.
- For Instagram 455, the share-intent Bundle hook and video-branch hook were byte-verified; a live paste was not repeated.

This is useful 455 evidence, but **there is no evidence here for 456.0.0.53.78**. The patch's declared target in the pinned manifest is 455.0.0.51.72. The 456 port must not be enabled until the target APK is inspected and all three hook families are independently resolved. The generic clipboard call-site scan must also be checked for new/removed call sites.

## Integration work required in IUP

- Import the Kotlin patch and Java helper with per-file GPL-3.0 provenance and notices.
- Replace the Oxbow-specific `com.feurstagram.*` extension namespace and framework constants with the IUP extension namespace/build graph.
- Do not import the full Oxbow `Config.java` or `Settings.java` wholesale. Bind the feature to IUP's unified persistent settings registry and Arabic/English string catalog; keep the setting key stable or migrate it explicitly.
- Add deterministic unit tests for URL cleaning, including allowlisted parameters, fragments, mixed text, multiple URLs, non-Instagram URLs, empty query values, and case-insensitive host/query handling.
- Add patch-application tests against the exact Instagram 456.0.0.53.78 APK for clipboard redirects, Bundle hook, and video branch.
- Runtime test Copy link and each relevant share route on a device after patch application.

## Port status

| Gate | Status |
|---|---|
| Immutable source and provenance identified | Passed |
| Kotlin/Java implementation and setting traced | Passed |
| Oxbow 454/455 evidence reviewed | Passed (upstream evidence only) |
| IUP source import | Not started |
| IUP settings/localization integration | Not started |
| 456.0.0.53.78 fingerprints verified | **Blocked / no evidence yet** |
| Build and runtime tests on 456 | Not run |
| Eligible for IUP release | No |

## Upstream source links

- [CleanLinksPatch.kt](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/patches/src/main/kotlin/com/feurstagram/patches/privacy/CleanLinksPatch.kt)
- [ShareLinks.java](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/extensions/extension/src/main/java/com/feurstagram/extension/ShareLinks.java)
- [Config.java](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/extensions/extension/src/main/java/com/feurstagram/extension/Config.java)
- [Settings.java](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/extensions/extension/src/main/java/com/feurstagram/extension/Settings.java)
- [OXBOW.md device verification notes](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/OXBOW.md)
