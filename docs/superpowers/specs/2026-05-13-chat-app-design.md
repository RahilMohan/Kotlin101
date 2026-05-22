# Design: Real-time Chat (Project 9)

**Goal:** Master Kotlin Serialization and Networking patterns.

## Architecture
- **Model:** `ChatMessage` (serializable).
- **Network:** `ChatService` (simulated WebSocket via Flow).
- **Serialization:** `kotlinx.serialization` for JSON conversion.

## Logic Decisions
- **Serialization:** Use `@Serializable` annotation on data classes.
- **Simulated WebSocket:** Use a `channelFlow` to emit random messages from a "server."
- **Backoff:** Demonstrate exponential backoff if the "connection" fails.

## Features
1. **Send Message:** Serialize object to JSON string.
2. **Receive Message:** Deserialize JSON string to object.
3. **Reactive Stream:** Flow of messages updating the "UI" state.
4. **Offline Sync (Concept):** Simulate WorkManager by queuing failed messages.
