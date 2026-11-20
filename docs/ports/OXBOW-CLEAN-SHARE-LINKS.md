# Port assessment: Oxbow — Clean share links

Audit date: 2026-11-20  
Decision: **Do not port into IUP. Duplicate feature already exists in the pinned HushGram baseline, with broader coverage and an Instagram 456 target.**

## Immutable Oxbow provenance reviewed

- Upstream: https://github.com/aic0d3r/oxbow
- Commit: `957e6a580fdaf4dd67e3ae8ee5384e223c85e94`
- License declared by upstream repository: GPL-3.0.
- Kotlin patch: `patches/src/main/kotlin/com/feurstagram/patches/privacy/CleanLinksPatch.kt`
- Java extension: `extensions/extension/src/main/java/com/feurstagram/extension/ShareLinks.java`
- Oxbow setting: `Config.isCleanLinks() -> getBlocked("clean_links", true)`; UI row: “Clean shared links”.

## Oxbow behavior and evidence

Oxbow redirects three clipboard API call types, inserts hooks in a share-intent Bundle builder and a video-share branch, and strips non-allowlisted query parameters from Instagram URLs on `instagram.com`, `instagr.am`, and `ig.me`. Its allowlist is `img_index`, `story_media_id`, `comment_id`, and `hl`.

Oxbow's own `OXBOW.md` records end-to-end Copy link/share-sheet testing on Instagram 454. For 455, it records byte verification of the Bundle and video hooks, but not a repeated live paste. The Oxbow manifest at the pinned commit declares 455.0.0.51.72, not 456.

## Duplicate found in HushGram (preferred implementation)

The pinned HushGram baseline already contains:
- Kotlin patch: [SanitizeSharingLinksPatch.kt](https://github.com/SysAdminDoc/HushGram/blob/da56d39d7c001bf052bcb7951e3051224bd39928/patches/src/main/kotlin/app/morphe/patches/instagram/misc/sharelinks/SanitizeSharingLinksPatch.kt)
- Java extension: [LinkCleaner.java](https://github.com/SysAdminDoc/HushGram/blob/da56d39d7c001bf052bcb7951e3051224bd39928/extensions/instagram/src/main/java/app/hushgram/extension/instagram/misc/LinkCleaner.java)
- Source commit: `da56d39d7c001bf052bcb7951e3051224bd39928`
- HushGram manifest target: Instagram `456.0.0.53.78`.

The HushGram implementation is materially broader than Oxbow's:
- Sanitizes parsed share/permalink responses, clipboard, chooser/share sheet, direct shares, and the in-app browser's Share/Copy actions.
- Covers ClipboardManager.setPrimaryClip as well as both setText APIs.
- Preserves non-tracking parameters and URL fragments, handles Instagram's rotating share IDs, supports an optional sharing domain, and separately handles browser click-tracker parameters and link shims.
- Uses the HushGram settings/status infrastructure and reports hook status/exception information.

This is a true behavior-level duplicate, not merely a similar name. Importing Oxbow's narrower implementation would create redundant/conflicting hooks and should be avoided.

## Temporary isolated experiment

A dependency-free URL-cleaner prototype and 10 regression assertions were briefly added to evaluate the Oxbow algorithm. The prototype passed local JDK 21 compilation/tests and a GitHub Actions run, but it is **not an accepted IUP implementation** and is being removed because the existing HushGram code is the better target-456 implementation. The test did not verify Android bytecode hooks or Instagram runtime behavior.

## Final decision

| Gate | Result |
|---|---|
| Oxbow source and provenance reviewed | Passed |
| Duplicate check against HushGram | **Duplicate confirmed** |
| Oxbow implementation imported into IUP | Rejected / removed |
| Preferred implementation | Keep HushGram Sanitize sharing links |
| 456.0.0.53.78 support evidence | HushGram manifest declares 456; independent runtime evidence still governed by the HushGram source/test pipeline |
| Eligible as an additional Oxbow port | No |

## Upstream Oxbow links

- [CleanLinksPatch.kt](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/patches/src/main/kotlin/com/feurstagram/patches/privacy/CleanLinksPatch.kt)
- [ShareLinks.java](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/extensions/extension/src/main/java/com/feurstagram/extension/ShareLinks.java)
- [OXBOW.md device verification notes](https://github.com/aic0d3r/oxbow/blob/957e6a580fdaf4dd67e3ae8ee5384e223c85e94e/OXBOW.md)
