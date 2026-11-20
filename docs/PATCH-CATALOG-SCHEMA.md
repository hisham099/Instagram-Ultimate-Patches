# Patch catalog schema

The machine-readable inventory lives at `patches/catalog.json`. It must contain only evidence-backed records. Do not add speculative entries or fabricate compatibility.

## Required fields

Each patch record must include:

- `id`: stable project-local identifier.
- `name`: display name.
- `description`: concise behavior description.
- `sources`: upstream source records with repository, commit, and path.
- `license`: SPDX identifier or `NOASSERTION`, plus notice notes.
- `status`: one of the statuses in [SOURCES.md](SOURCES.md).
- `target`: Instagram package and exact version tested.
- `compatibility_evidence`: build/test references or an explicit empty array.
- `duplicates`: related catalog IDs and resolution.
- `localization`: Arabic and English coverage.
- `notes`: known limitations.

## Empty initial catalog

The catalog starts empty intentionally. It will be populated after source retrieval and review; an empty catalog is more accurate than invented patch metadata.
