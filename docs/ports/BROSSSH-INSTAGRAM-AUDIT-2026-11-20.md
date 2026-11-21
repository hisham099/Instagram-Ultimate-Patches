# Brosssh Instagram patch audit / تدقيق رقع Brosssh

Audit date: 2026-11-20  
Repository: [Luen/brosssh-morphe-patches](https://github.com/Luen/brosssh-morphe-patches)  
Pinned commit: `de158a4ebfc5376a71058b2c012a9895e46cdb14`  
Declared Instagram target: `439.0.0.37.89` (ARM64 version code `384510827`; ARMv7 `384510838`)  
IUP target: `456.0.0.53.78`

## Executive decision

**No Brosssh patch is imported or certified for IUP/Instagram 456 in this audit.** The source is genuine Kotlin patch implementation, not just a patch-name list, but its pinned compatibility is 439.0.0.37.89. Its fingerprints and MobileConfig IDs cannot be assumed to work on 456.

The recursive pinned tree contains 19 Instagram Kotlin source files. The Instagram manifest is the authoritative patch-list source; helper file `FilterStoriesListPatch.kt` is a shared implementation helper and is not itself a standalone manifest patch. The source also has Java extension code for navigation/feed behavior. The repository uses the Morphe patch framework and extension APIs, so copying isolated Kotlin files into IUP would not create working patches.

## Candidate-by-candidate disposition

| Brosssh source feature | IUP/HushGram comparison | Audit disposition |
|---|---|---|
| Disable Reels scrolling | IUP has **Stop Reels scrolling** | Semantic overlap; compare implementation before any replacement |
| Disable story auto flipping | IUP has **Stop Story auto-advance** | Semantic overlap |
| Disable video autoplay | IUP has **Tap to play** and playback controls; exact behavior may differ | Partial overlap; retain as candidate, do not merge blindly |
| Filter stories list (helper) | IUP has story filtering/hiding features | Shared helper; not standalone |
| Hide ads | IUP has **Hide ads** | Duplicate feature |
| Hide all stories | IUP has **Hide highlights**, **Hide suggested stories**, and other story controls; no exact same scope established from catalog alone | Potentially distinct scope; needs semantic/source comparison |
| Hide Explore feed | IUP has **Hide the Explore grid** and **Explore without personalization** | Partial overlap; Brosssh removes Explore response sections |
| Hide feed content | IUP has **Hide the home feed** | Similar goal, potentially different implementation/scope |
| Hide Instants | IUP has **Hide Instants** | Duplicate feature |
| Hide navigation buttons | IUP has **Hide the Reels tab**, tab-bar layout and related navigation controls | Partial overlap; Brosssh exposes per-button options and depends on extension code |
| Hide notes tray | IUP has **Hide the notes row** | Likely duplicate scope; verify UI location before merging |
| Hide Reels save button | No exact catalog-name match confirmed | Candidate; MobileConfig override requires 456 validation |
| Hide reshare button | IUP has **Hide the Repost button** | Semantic overlap |
| Hide suggested content | IUP has suggested posts/stories/accounts and Reels filtering controls | Broad overlap; compare exact option coverage |
| Hide suggested Reels (helper) | Included by Hide suggested content | Helper, not standalone |
| Hide Threads profile button | IUP has **Hide the Threads button** | Duplicate feature |
| Limit feed to following profiles | IUP has **Start Home on Following** and feed controls | Similar outcome, but request-header mutation may be distinct; candidate for deeper comparison |
| Bypass signature check | IUP has **Restore trust on re-signed builds** | Related security/signature area; do not combine without reviewing threat model and implementation |
| Clone Instagram | IUP has **Install next to Instagram** | Duplicate purpose; compare package/name handling |
| Remove build expired popup | IUP has **Remove build expired popup** | Duplicate feature |

## Code-level observations

- `DisableReelsScrollingPatch.kt` disables ViewPager2 user input and returns false from the Reels swipe-refresh intercept method. It also overrides MobileConfig `83371::0`.
- `DisableStoryAutoFlippingPatch.kt` returns early from the story timeout action method.
- `DisableVideoAutoplayPatch.kt` forces the `ig_disable_video_autoplay` boolean fingerprint to true.
- `HideAdsPatch.kt` returns false from the “Is ad pod” method and overrides MobileConfig `110800::0`.
- `HideExploreFeedPatch.kt` replaces Explore response JSON fields `sectional_items` and `clusters`.
- `HideFeedContentPatch.kt` replaces the `feed_items` JSON field in the feed parser.
- `HideInstantsPatch.kt` disables two MobileConfig variants (`71567::9`, `71567::42`).
- `HideNotesTrayPatch.kt` hides the NotesTrayAdapter view and sets zero-sized layout parameters.
- `HideReelsSaveButtonPatch.kt` changes MobileConfig `81702::5`.
- `HideReshareButtonPatch.kt` forces `enable_media_notes_production` false in both a media parser and a Pando boolean lookup.
- `HideSuggestedContentPatch.kt` combines MobileConfig, story filtering, URL blocking and suggested-Reels filtering.
- `LimitFeedToFollowingProfilesPatch.kt` rewrites the feed request's `pagination_source` header to `following` through a Java extension helper.
- `HideNavigationButtonsPatch.kt` uses a Java extension helper and exposes six independent button options.

These observations describe the pinned 439 source. They are **not** proof that the fingerprints, IDs, parser fields, or injected instructions match Instagram 456.

## Why no automatic port was performed

1. The declared target is 439, not 456. Updating a manifest version string would be unsafe and misleading.
2. The patch framework APIs, shared Morphe library helpers, extension classes, and dependencies must be mapped into IUP's actual build graph.
3. Several candidates overlap existing IUP functionality. Importing them without behavioral comparison would create duplicate settings and conflicting hooks.
4. The IUP repository currently has no supplied/extracted Instagram 456 APK or verified 456 DEX fingerprints in this task context. Therefore no 456 patch-application test can be honestly claimed.

## Next candidates worth deeper work

Prioritize these for source-to-source comparison against HushGram before deciding whether to port:
- **Hide Reels save button** — no exact catalog-name match; validate whether the MobileConfig flag still exists in 456.
- **Disable video autoplay** — determine whether HushGram's Tap to play is equivalent or whether Brosssh blocks autoplay more completely.
- **Hide all stories** — compare scope with story tray controls.
- **Limit feed to following profiles** — compare with Start Home on Following; inspect whether IUP already rewrites request headers.
- **Hide feed content / Hide Explore feed** — compare parser-level behavior and fallback safety.

## References

- Pinned Brosssh source: https://github.com/Luen/brosssh-morphe-patches/tree/de158a4ebfc5376a71058b2c012a9895e46cdb14
- Brosssh manifest: https://github.com/Luen/brosssh-morphe-patches/blob/de158a4ebfc5376a71058b2c012a9895e46cdb14/patches-list.json
- Brosssh target constants: https://github.com/Luen/brosssh-morphe-patches/blob/de158a4ebfc5376a71058b2c012a9895e46cdb14/patches/src/main/kotlin/app/morphe/patches/Constants.kt
- IUP catalog: [`patches/catalog.json`](../../patches/catalog.json)
- IUP compatibility policy: [`docs/COMPATIBILITY.md`](../COMPATIBILITY.md)

---

## الملخص العربي

تم فحص الشيفرات الفعلية لـBrosssh عند الالتزام المحدد، وليس الاكتفاء بأسماء الرقع. يحتوي المصدر على 19 ملف Kotlin خاصاً بإنستغرام، ويعلن التوافق مع الإصدار **439.0.0.37.89** فقط، بينما هدف IUP هو **456.0.0.53.78**.

توجد رقع متداخلة مع HushGram مثل إخفاء الإعلانات وInstants وزر Threads وإزالة نافذة انتهاء البناء، كما توجد رقع تحتاج مقارنة أعمق مثل تعطيل التشغيل التلقائي للفيديو، إخفاء جميع القصص، إخفاء زر حفظ Reels، وتقييد الخلاصة بالحسابات المتابَعة.

**لم يتم استيراد أي رقعة من Brosssh ولم يتم اعتمادها للإصدار 456.** السبب أن بصمات DEX ومعرّفات MobileConfig القديمة لا تثبت التوافق مع 456، كما أن بعض الرقع تعتمد على مكتبة Morphe وامتدادات Java لا يمكن حذفها عند النقل. الأولوية التالية هي التحقق من المرشحين المختلفين دلالياً ثم اختبارهم على APK 456 فعلي قبل دمجهم.


## Deeper comparison against the pinned HushGram implementation

A second source-level pass checked the actual HushGram Kotlin implementations rather than relying only on catalog labels:

- **Limit feed to following profiles — keep as a distinct candidate, not a duplicate.** Brosssh's Java helper copies the request header map and changes `pagination_source` from `feed_recs` (or null) to `following` on default-feed requests. HushGram's `StartOnFollowingPatch.kt` implements Instagram's own feed picker and remembered-feed flags, with additional user choices (remove For you, fallback behavior, mixing). These are related but not equivalent implementations or UX. Porting Brosssh's forced-header behavior could conflict with the HushGram picker and should be evaluated as an optional mode, not merged as a second unconditional hook.
- **Hide feed content — behavior substantially overlaps, implementation differs.** HushGram's `Hide the home feed` filters Home's feed items, including reads from the cached feed, and keeps the separate stories row; it also coordinates the empty-feed UI. Brosssh's older patch replaces the `feed_items` JSON field in a parser. HushGram's implementation is broader and more carefully scoped, so do not import Brosssh's parser hook as a duplicate.
- **Hide Explore feed — overlaps but scope differs.** HushGram has `Hide the Explore grid` and other Explore controls; Brosssh replaces Explore response fields `sectional_items` and `clusters`. The Brosssh implementation is not accepted as an exact duplicate without checking the 456 response shape and intended scope.
- **Disable video autoplay — not proven duplicate.** HushGram's `Tap to play` gates playback across videos, Reels and stories, and includes user-facing behavior beyond simply forcing an autoplay flag. Brosssh's patch only forces the `ig_disable_video_autoplay` fingerprint to true. Treat this as a possible narrower/alternative mode, not a replacement for Tap to play.
- **Stop Reels scrolling — duplicate outcome; HushGram is more configurable.** HushGram's pinned source explicitly documents the Reels pager lookup string as present from 456 onward and adds several source-aware controls. Brosssh's implementation disables ViewPager2 input and pull refresh and also changes MobileConfig. Do not import it over the existing HushGram patch.
- **Hide notes tray — likely same user-visible area, but confirm location before rejection.** HushGram's `Hide the notes row` filters the inbox sections and Map bubble; Brosssh hides a NotesTrayAdapter view in the Direct tab. This is a strong overlap, but the exact view and any Map-bubble behavior differ.
- **Hide all stories — no exact one-switch counterpart identified.** HushGram has separate controls for suggested stories/highlights and story elements. Brosssh filters suggested, highlighted, and user story entries from the tray through a shared helper. This remains a candidate if users want a complete story-tray removal switch, but must be checked for side effects and 456 parser compatibility.
- **Hide Reels save button — strongest simple candidate in this Brosssh set.** No exact HushGram catalog patch was found for this specific button. Its implementation is only a MobileConfig override (`81702::5`), so it is a small port candidate but cannot be certified until that flag is checked in the 456 APK/config.
- **Hide navigation buttons — overlap with existing tab/navigation controls, but Brosssh offers a different per-button implementation.** Its helper reflectively removes enum entries from the navigation list. HushGram already has Hide the Reels tab and tab-bar controls. Do not add a competing global navigation hook without testing interactions and settings.

### Updated candidate priority

1. **Hide Reels save button** — unique behavior candidate; validate MobileConfig flag on 456.
2. **Hide all stories** — potentially useful extra scope; map its filtering effects and test on 456.
3. **Limit feed to following profiles** — consider as an optional forced-following mode only if it can coexist cleanly with HushGram's picker.
4. **Disable video autoplay** — compare user-facing semantics; likely not a replacement for Tap to play.
5. **Hide Explore feed** — lower priority because HushGram already has Explore controls and Brosssh's scope is unclear.

The Brosssh source's 439 compatibility declaration remains the only compatibility claim for these patches. The comparison above improves the deduplication decision but does not make any Brosssh patch compatible with 456.

## Hide Reels save button — exact implementation follow-up (2026-11-21)

Pinned source file:
`patches/src/main/kotlin/app/morphe/patches/instagram/patches/distractionFree/HideReelsSaveButtonPatch.kt`

The implementation is intentionally small:

- Declares a bytecode patch named `Hide Reels save button`, with `default = false`.
- Declares compatibility through Brosssh's Instagram compatibility constant, whose pinned manifest target is `439.0.0.37.89`.
- Depends on Morphe's `overrideMobileConfigBooleanFlag` helper.
- Requests the MobileConfig override `81702::5` to `true`, with the source comment identifying the flag as `ig_channels_4th_ufi::android_hide_save_button`.
- The pinned manifest lists only `BytecodePatch` as a dependency and has no options. No additional Brosssh Java setting or extension accessor is declared for this patch.

### Port assessment

This is a lower-coupling candidate than the Piko gesture feature: it has no patch-specific Java preference or settings-screen dependency in the pinned Brosssh source. It is still not a safe blind import. The actual `81702::5` flag identifier/value must be confirmed against Instagram 456.0.0.53.78 (or a matching MobileConfig schema), and the Morphe helper's current IUP equivalent must be identified. The comment in an old source file is not proof that the flag remains valid or has the same meaning in 456.

**Decision:** keep as the first Brosssh port candidate after the Piko dependency mapping. Do not add it to the 456-compatible catalog or enable it in a release until the flag is validated and a patch-application/build test passes.

Source: [HideReelsSaveButtonPatch.kt at the pinned Brosssh commit](https://github.com/Luen/brosssh-morphe-patches/blob/de158a4ebfc5376a71058b2c012a9895e46cdb14/patches/src/main/kotlin/app/morphe/patches/instagram/patches/distractionFree/HideReelsSaveButtonPatch.kt).