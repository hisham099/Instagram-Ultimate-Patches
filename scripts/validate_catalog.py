#!/usr/bin/env python3
"""Validate the normalized Instagram Ultimate Patches catalog (stdlib only)."""
import json
import re
import sys
from pathlib import Path

CATALOG = Path(__file__).resolve().parents[1] / "patches" / "catalog.json"
TARGET_PACKAGE = "com.instagram.android"
TARGET_VERSION = "456.0.0.53.78"


def fail(message: str) -> None:
    print(f"ERROR: {message}", file=sys.stderr)
    raise SystemExit(1)


try:
    data = json.loads(CATALOG.read_text(encoding="utf-8"))
except Exception as exc:
    fail(f"cannot parse {CATALOG}: {exc}")

if data.get("schemaVersion") != 1:
    fail("schemaVersion must be 1")
target = data.get("target", {})
if target.get("packageName") != TARGET_PACKAGE:
    fail("unexpected target package")
if target.get("version") != TARGET_VERSION:
    fail("unexpected target version")

patches = data.get("patches")
if not isinstance(patches, list) or not patches:
    fail("patches must be a non-empty array")

ids = [p.get("id") for p in patches]
if any(not isinstance(value, str) or not value for value in ids):
    fail("every patch must have a non-empty id")
if len(ids) != len(set(ids)):
    fail("duplicate patch ids")

allowed_review = {"upstream-declared-only", "source-reviewed", "build-verified", "runtime-verified", "deferred", "rejected"}
allowed_integration = {"not-integrated", "integrated", "deferred", "rejected"}
for patch in patches:
    if not patch.get("name"):
        fail(f"{patch.get('id')}: missing name")
    if patch.get("reviewStatus") not in allowed_review:
        fail(f"{patch.get('id')}: invalid reviewStatus")
    if patch.get("integrationStatus") not in allowed_integration:
        fail(f"{patch.get('id')}: invalid integrationStatus")
    source = patch.get("source", {})
    if not re.fullmatch(r"[0-9a-f]{40}", source.get("commit", "")):
        fail(f"{patch.get('id')}: source commit must be a full 40-character SHA")
    if not source.get("repository") or not source.get("file"):
        fail(f"{patch.get('id')}: incomplete source provenance")

counts = data.get("counts", {})
if counts.get("total") != len(patches):
    fail("counts.total does not match patches array")
if counts.get("uniqueIds") != len(set(ids)):
    fail("counts.uniqueIds does not match")
print(f"Catalog OK: {len(patches)} records; target {TARGET_PACKAGE} {TARGET_VERSION}.")
print("Note: upstream-declared compatibility is not independent build/runtime verification.")
