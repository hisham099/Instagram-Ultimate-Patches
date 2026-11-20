# Instagram Ultimate Patches (IUP)

**A unified, Instagram-only Morphe Patches project.**  
**مشروع موحّد لرقع إنستغرام فقط ضمن منظومة Morphe Patches.**

> **Project status / حالة المشروع:** Multi-source source audit and 194-entry HushGram baseline catalog committed. Piko, Brosssh, Oxbow and FeurStagram implementation trees are mapped; source import, per-patch porting, independent compatibility verification, and unified IUP MPP release are **not yet complete**.

## Goal / الهدف

Build one curated MPP package for **Instagram Android 456.0.0.53.78**, combining carefully reviewed patches from multiple upstream projects while preserving original HushGram functionality where licensing and compatibility permit.

إنشاء حزمة MPP واحدة منتقاة لإصدار **Instagram Android 456.0.0.53.78**، مع مراجعة الرقع من عدة مصادر والحفاظ على وظائف HushGram الأصلية متى سمحت التراخيص والتوافق.

## Project principles / مبادئ المشروع

- Instagram only. This repository is independent from the HushGram app project.
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
- `patches/` — normalized catalog and candidate inventory; multi-source implementation import/porting is in progress.
- `.github/workflows/` — catalog validation and pinned HushGram baseline MPP build.

## Current verification / التحقق الحالي

- GitHub repository access: verified.
- Default branch: `main`.
- Source audit pinned to exact upstream commits: see `docs/UPSTREAM-AUDIT-2026-11-20.md`.
- Catalog: 194 HushGram manifest records (190 upstream-enabled, 4 disabled); all declare `456.0.0.53.78`.
- Candidate index: 105 records from Piko (66), Brosssh (18), and Oxbow (21); all declare older targets and remain unintegrated.
- HushGram README/manifest discrepancy (186 vs 194) and Oxbow release/manifest discrepancy (19 vs 21) are documented and remain open.
- HushGram LICENSE and NOTICE preserved under `third_party/hushgram/`.
- Catalog validation workflow: **passed** on commit `b69bd2977d697d9f0d92771f69d7af5b68b0aaea`.
- Catalog metadata is upstream-declared only; no patch is yet independently certified or integrated here.
- Pinned HushGram baseline MPP build: **passed** on commit `911923de4f9047faba4e07cda470296750d87c4e`; artifact `hushgram-baseline-0.0.21-911923de4f9047faba4e07cda470296750d87c4e` uploaded. This is not an IUP release.
- Piko/Brosssh/Oxbow/FeurStagram Kotlin and Java implementation roots were located and inventoried; see `docs/MULTI-SOURCE-IMPLEMENTATION-INTAKE-2026-11-20.md`.
- First Oxbow candidate reviewed at code level: Clean share links was rejected as a duplicate because HushGram already implements a broader version with a declared 456 target; see `docs/ports/OXBOW-CLEAN-SHARE-LINKS.md`.
- First distinct Piko candidate reviewed at code level: `Disable disappearing messages swipe`; it is unique in the preliminary HushGram search but still needs exact 456 bytecode fingerprint validation before porting; see `docs/ports/PIKO-DISAPPEARING-MESSAGES-GESTURE.md`.
- Additional-source code is not yet part of the IUP build graph; no extra-source patch is certified for 456.0.0.53.78.
- MPP artifact for the merged IUP project: not built yet.

## العربية

هذا المستودع مخصص لرقع Instagram Android فقط. لن تُعتبر الرقعة جاهزة لمجرد وجودها في مشروع آخر أو لأنها تعمل على إصدار أقدم. يجب مراجعة مصدرها وترخيصها واعتمادياتها وتغييرات واجهات Instagram، ثم اجتياز البناء والاختبارات المناسبة قبل إدراجها في إصدار نهائي.

## License and attribution

No blanket license is asserted for third-party code. Each imported component must retain its original license and notices. The repository's own documentation does not relicense upstream material.
