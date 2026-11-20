# Instagram Ultimate Patches (IUP)

**A unified, Instagram-only Morphe Patches project.**  
**مشروع موحّد لرقع إنستغرام فقط ضمن منظومة Morphe Patches.**

> **Project status / حالة المشروع:** Repository bootstrap and access verified. Patch-source integration, target-version compatibility, and MPP release are **not yet verified or complete**.

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

See [docs/SOURCES.md](docs/SOURCES.md). Candidate projects include HushGram, Morphe, Piko, Brosssh, Oxbow, and FeurStagram. Inclusion in this list is not an endorsement or a compatibility claim.

## Repository layout / بنية المستودع

- `docs/` — source review, compatibility decisions, and contribution rules.
- `patches/catalog.json` — normalized patch inventory; entries must be evidence-backed.
- `patches/` — integrated patch implementation (to be added after source and license review).
- `.github/workflows/` — reproducible validation and build automation (to be added).

## Current verification / التحقق الحالي

- GitHub repository access: verified.
- Default branch: `main`.
- Repository was empty at bootstrap.
- Target Instagram version: `456.0.0.53.78`.
- MPP artifact: not built yet.
- No patch is currently certified compatible by this repository.

## العربية

هذا المستودع مخصص لرقع Instagram Android فقط. لن تُعتبر الرقعة جاهزة لمجرد وجودها في مشروع آخر أو لأنها تعمل على إصدار أقدم. يجب مراجعة مصدرها وترخيصها واعتمادياتها وتغييرات واجهات Instagram، ثم اجتياز البناء والاختبارات المناسبة قبل إدراجها في إصدار نهائي.

## License and attribution

No blanket license is asserted for third-party code. Each imported component must retain its original license and notices. The repository's own documentation does not relicense upstream material.
