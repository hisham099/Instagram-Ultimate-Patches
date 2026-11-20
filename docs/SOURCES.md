# Upstream sources and review register

This register records candidate sources, not automatic inclusion. Every imported patch requires an exact source revision, license review, implementation review, and target-version validation.

## Candidate register

| Source | Candidate role | Current decision |
|---|---|---|
| HushGram | Primary baseline and original patch set | Review first; preserve eligible original patches |
| Morphe | Patch framework, conventions, and upstream baseline | Review framework/version alignment |
| Piko | Additional Instagram patches | Candidate only; older target metadata must be revalidated |
| Brosssh | Additional patch candidates | Candidate only; exact source revision and target support required |
| Oxbow | Additional patch candidates / FeurStagram-derived fork | Candidate only; published bundle metadata references Instagram 455.0.0.51.72, not the target |
| FeurStagram | Additional patch candidates | Candidate only; verify current repository, license, and exact target compatibility |

## Known review cautions

- A patch bundle targeting Instagram 455.0.0.51.72 is not evidence of compatibility with 456.0.0.53.78.
- A source guide mentioning 452.x or another older version is not evidence of compatibility with 456.x.
- Forks may duplicate upstream patches. Compare implementation and behavior, not labels.
- Preserve required upstream notices. HushGram's NOTICE file was previously observed to require retention in source distributions and derivative works; verify the exact current license/NOTICE text before importing code.
- Counts previously observed during preliminary exploration are provisional and must not be treated as a current audited inventory.

## Required evidence for every imported patch

1. Upstream repository URL and immutable commit SHA.
2. Source file path(s) and original patch identifier.
3. License and notice obligations.
4. Instagram version/API assumptions and affected classes/methods/resources.
5. Duplicate/conflict analysis against the existing catalog.
6. Adaptation commit, if modified.
7. Build result against the pinned target dependencies.
8. Runtime/device verification where feasible.
9. Arabic and English user-facing strings, if the patch exposes settings.

## Status vocabulary

- `candidate`: discovered, not yet reviewed.
- `reviewing`: source/license/implementation review underway.
- `adapted`: modified for the target; not necessarily validated.
- `build-verified`: included in a successful reproducible build.
- `runtime-verified`: additionally exercised on the target app/device.
- `deferred`: potentially useful but blocked by a known issue.
- `rejected`: excluded with a documented reason.

A status must never be promoted without evidence linked in the patch record.
