# Instagram Ultimate Patches (IUP)

**A unified, Instagram-only Morphe Patches project.**  
**مشروع موحّد لرقع إنستغرام فقط ضمن منظومة Morphe Patches.**

> **Project status / حالة المشروع:** HushGram's Gradle/patch/extension architecture is now established as IUP's canonical implementation model. The repository is still in the source-foundation and multi-source porting stage; the complete HushGram source tree has not yet been imported into the IUP root, extra-source ports are not yet built, and the unified IUP MPP release is **not yet complete**.

## Goal / الهدف

Build one curated MPP package for **Instagram Android 456.0.0.53.78**, combining carefully reviewed patches from multiple upstream projects while preserving original HushGram functionality where licensing and compatibility permit.

إنشاء حزمة MPP واحدة منتقاة لإصدار **Instagram Android 456.0.0.53.78**، مع مراجعة الرقع من عدة مصادر والحفاظ على وظائف HushGram الأصلية متى سمحت التراخيص والتوافق.

## Project principles / مبادئ المشروع

- Instagram only. IUP is a distinct multi-source MPP product that reuses HushGram's proven Morphe patch architecture and preserves upstream attribution.
- Preserve upstream attribution, license files, and required notices.
- Never label a patch compatible with 456.0.0.53.78 without source/API review and a successful build or runtime verification.
- Deduplicate by behavior and implementation, not by patch name alone.
- Keep an inventory of accepted, adapted, deferred, and rejected patches with reasons.
- Arabic and English documentation and in-app localization; RTL/LTR support.
- Do not redistribute upstream APKs. The deliverable is patch source/catalog and the MPP build artifact where licensing permits.

## Candidate upstreams / المصادر المرشحة

See [docs/SOURCES.md](docs/SOURCES.md) and [the pinned audit](docs/UPSTREAM-AUDIT-2026-11-20.md). HushGram is the primary baseline; Piko, Brosssh, Oxbow and FeurStagram are older-target candidates requiring selective porting. Morphe is the patch framework reference.

## Repository layout / بنية المستودع

- `docs/` — source review, compatibility decisions, and contribution rules.
- `patches/catalog.json` — 194 normalized HushGram manifest records with source commit and upstream compatibility metadata.
- `patches/` — currently normalized catalog and candidate inventory; the target source layout follows HushGram's Kotlin patch tree, Java Instagram extension, shared settings library, Gradle build and tests.
- `.github/workflows/` — catalog validation and pinned HushGram baseline MPP build.

## Current verification / التحقق الحالي

- GitHub repository access: verified.
- Default branch: `main`.
- Source audit pinned to exact upstream commits: see `docs/UPSTREAM-AUDIT-2026-11-20.md`.
- Catalog: 194 HushGram manifest records (190 upstream-enabled, 4 disabled); all declare `456.0.0.53.78`.
- Candidate index: 105 records from Piko (66), Brosssh (18), and Oxbow (21); all declare older targets and remain unintegrated.
- HushGram README/manifest discrepancy (186 vs 194) and Oxbow release/manifest discrepancy (19 vs 21) are documented and remain open.
- HushGram LICENSE and NOTICE preserved under `third_party/hushgram/`.
- Catalog validation workflow: **passed** on commit `e26903ce63984f618b3fac80f61f1ae202840940` (run [#44901878505](https://github.com/hisham099/Instagram-Ultimate-Patches/actions/runs/44901878505)); newer documentation-only commits are revalidating.
- Catalog metadata is upstream-declared only; no patch is yet independently certified or integrated here.
- Pinned HushGram baseline MPP build: **passed** on commit `e26903ce63984f618b3fac80f61f1ae202840940` (run [#44901878512](https://github.com/hisham099/Instagram-Ultimate-Patches/actions/runs/44901878512)). This proves the pinned upstream baseline builds; it is not an IUP merged release.
- Piko/Brosssh/Oxbow/FeurStagram Kotlin and Java implementation roots were located and inventoried; see `docs/MULTI-SOURCE-IMPLEMENTATION-INTAKE-2026-11-20.md`.
- Brosssh source-level review: 19 Instagram Kotlin implementation files inspected at pinned commit; candidate overlaps and deferred features recorded in [the Brosssh audit](docs/ports/BROSSSH-INSTAGRAM-AUDIT-2026-11-20.md). No Brosssh code has been imported or certified for 456.
- Piko source-level review: the disappearing-messages swipe gesture is confirmed as a distinct candidate after semantic comparison with HushGram. Kotlin patch, persistent setting, runtime getter, availability flag, settings-screen gate and the broad settingsPatch dependency graph are traced. IUP must adapt this feature to its own settings registry rather than import Piko's full settings subsystem. It remains unported and unverified on 456; see [the Piko implementation review](docs/ports/PIKO-DISABLE-DISAPPEARING-MESSAGES-SWIPE.md).
- First Oxbow candidate reviewed at code level: Clean share links was rejected as a duplicate because HushGram already implements a broader version with a declared 456 target; see [the Oxbow review](docs/ports/OXBOW-CLEAN-SHARE-LINKS.md).
- Additional-source code is not yet part of the IUP build graph; no extra-source patch is certified for 456.0.0.53.78.
- HushGram source architecture inspected directly: Gradle project, Kotlin patch classes, Java runtime extensions, shared settings/localization library, patch availability status, source tests, provenance and generated patch catalog.
- APK upload is not a prerequisite for source integration, settings wiring, catalog generation, compilation, fixture-independent tests or baseline MPP build. HushGram's pinned `AppCompatibilities.kt` already declares target `456.0.0.53.78` and its ABI build codes. Exact APK/DEX evidence is a separate gate for independently rechecking extra-source fingerprints and patch application.
- IUP source integration is not yet complete: the current repository remains metadata/docs-first, and the baseline workflow still builds a separate pinned HushGram checkout. The next structural milestone is importing the complete HushGram Gradle source tree into IUP itself, then porting selected features into its native packages.
- MPP artifact for the merged IUP project: not built yet.

## العربية

هذا المستودع مخصص لرقع Instagram Android فقط. لن تُعتبر الرقعة جاهزة لمجرد وجودها في مشروع آخر أو لأنها تعمل على إصدار أقدم. يجب مراجعة مصدرها وترخيصها واعتمادياتها وتغييرات واجهات Instagram، ثم اجتياز البناء والاختبارات المناسبة قبل إدراجها في إصدار نهائي.

## License and attribution

No blanket license is asserted for third-party code. Each imported component must retain its original license and notices. The repository's own documentation does not relicense upstream material.
