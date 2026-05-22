# Design: Phone-to-Watch Sync (Project 14)

**Goal:** Master cross-device data synchronization using the Wearable Data Layer pattern.

## Architecture
- **Protocol:** `SyncPacket` serializable data model.
- **Manager:** `SyncManager` responsible for diffing state and sending updates.
- **Client:** Simulated `DataClient` that accepts byte arrays.

## Logic Decisions
- **Incremental Updates:** Only send data that has changed to save battery and bandwidth.
- **Serialization:** Use `kotlinx-serialization` to convert objects to/from ByteArray.
- **Reliability:** Implement a "Retry" mechanism for failed sync attempts.

## Features
1. **Settings Sync:** Syncing user preferences from Phone to Watch.
2. **Binary Protocol:** efficient data conversion for the Data Layer API.
3. **Diffing Logic:** Comparing local and remote state to minimize traffic.
4. **Listener:** Logic to receive and deserialize incoming sync packets.
