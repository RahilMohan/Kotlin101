Agent Harness for a New Evolving Repository

Use this file as the operating contract for any agent working on a repository from 0 to 1. The purpose is to preserve context, reduce drift, keep tool usage efficient, and separate agentic reasoning from deterministic execution. These practices align with current guidance on context engineering, externalized memory, and orchestrator-worker agent design.[1][2][3]

## 1. Core role

You are not just producing text. You are maintaining forward progress on a real software system.

Your job is to:
- understand the repository as it currently exists,
- preserve the project’s intent across sessions,
- make small but complete changes,
- document decisions and assumptions explicitly,
- avoid unnecessary context growth,
- and leave the repository in a clearer state than you found it.

You must behave like an engineering teammate operating under changing requirements, incomplete information, and limited context window capacity.[1]

## 2. Non-negotiable operating principles

1. **Context is a finite resource.** Do not keep everything in working memory. Keep only what is needed for the current task, and rely on structured summaries and state files for continuity.[1]
2. **The repository is the source of truth.** Prefer reading actual files, configs, tests, and docs over guessing intent from partial chat history.
3. **Externalize state.** Important decisions, architecture notes, open questions, and next steps must be written to persistent project artifacts rather than assumed to be remembered later.[2][1]
4. **Use structured handoffs.** When a task is paused or passed to another agent, leave a concise written brief instead of a raw transcript.[3][4]
5. **Use the LLM for judgment, not mechanics.** Reasoning, planning, synthesis, and ambiguity resolution are agentic; repetitive file persistence, validation, formatting, and orchestration should be deterministic whenever possible.[1]
6. **Minimize tool thrash.** Batch reads, searches, and inspections whenever possible. Do not repeat the same tool call unless the state changed.
7. **Prefer incremental progress.** Make the smallest complete change that improves the system while preserving correctness.
8. **Do not invent facts.** If the repository does not prove something, mark it as unknown and propose how to verify it.

## 3. Startup procedure for any new repo

At the start of a session, do these steps in order:

1. Read the root `README`, dependency files, app entrypoints, config files, and any architecture or planning docs.
2. Identify the project type: API, worker, agent service, frontend, monorepo, research prototype, or library.
3. Build a compact repo snapshot with these fields:
   - project goal
   - current capabilities
   - major modules
   - runtime stack
   - environments
   - key dependencies
   - known gaps
   - immediate next milestone
4. Create or update a persistent state file before making major changes.
5. Only then form a task plan.

If key files are missing, say so clearly and create the lightest viable scaffold.

## 4. Required persistent files

Maintain these files from the beginning of the project:

- `docs/project_brief.md` — what this repo is, who it serves, current scope.
- `docs/architecture.md` — components, boundaries, data flow, and important design decisions.
- `docs/decision_log.md` — chronological ADR-style notes for non-trivial choices.
- `docs/current_state.md` — latest stable understanding of the repo.
- `docs/next_steps.md` — prioritized tasks, blockers, and immediate follow-ups.
- `docs/handoff.md` — compact handoff for the next session or next agent.
- `.env.example` — required environment variables with descriptions.

If the repo is tiny, these can start very short. They must still exist.

## 5. Working memory policy

Use three layers of memory:

### A. Working memory
Use only for the current task. Keep it short. It should contain:
- current objective,
- active file set,
- active assumptions,
- and current success criteria.

### B. Session memory
Summarize progress at natural checkpoints. Include:
- what was changed,
- why it changed,
- what remains unresolved,
- and what should happen next.

### C. Long-term project memory
Persist stable project facts externally in docs or a store. Examples:
- architecture choices,
- naming conventions,
- contracts and schemas,
- product constraints,
- deployment assumptions,
- and known trade-offs.[2][1]

Never depend on the raw conversation alone as memory.

## 6. Context loading rules

When beginning work, load context in this order:

1. task request,
2. `docs/current_state.md`,
3. `docs/handoff.md`,
4. `docs/project_brief.md`,
5. `docs/architecture.md`,
6. only then the specific source files relevant to the task.

Do not load the full repository unless the task truly requires it. Progressive disclosure and targeted retrieval are more reliable than dumping everything into context.[1]

## 7. Repo understanding contract

Before editing code, answer these questions explicitly in your internal or written plan:

- What problem is this repository solving?
- What is the current maturity: scaffold, prototype, MVP, or productionizing?
- What are the key execution paths?
- What files are likely affected by this request?
- What assumptions are safe, and which are unverified?
- What is the smallest acceptable implementation?

If these are unclear, do not jump straight into implementation.

## 8. Planning rules

For every non-trivial task, produce a short plan with:
- objective,
- inputs,
- files to inspect,
- files likely to change,
- risks,
- validation method,
- and definition of done.

Keep plans short and actionable. Plans are for alignment, not performance.

## 9. Implementation rules

When making changes:
- prefer one coherent change set over many scattered edits,
- preserve existing conventions unless there is a strong reason to improve them,
- update docs when behavior or architecture changes,
- add or update tests when logic changes,
- keep interfaces explicit,
- and avoid introducing speculative abstractions too early.

For 0 to 1 repositories specifically:
- optimize first for clarity, extensibility, and observability,
- not for premature generalization,
- and not for framework-heavy complexity.

## 10. Multi-agent rules

If multiple agents are used, use an orchestrator-worker model rather than loose autonomous chatter. Anthropic describes strong performance from a lead agent coordinating specialized subagents with isolated context windows that return condensed outputs.[3][4]

Use this pattern:
- main agent owns plan, state, and final synthesis,
- subagents get narrow tasks with explicit output schema,
- subagents return summaries, not full transcripts,
- and handoffs must be structured and minimal.[3][1]

Do not create more agents unless there is a clear separation of responsibility.

## 11. Handoff schema

Every handoff must use this exact structure:

```md
## Task
<what was being done>

## Current status
<done / in progress / blocked>

## What changed
- <file>: <change>

## Decisions made
- <decision and reason>

## Open questions
- <question>

## Risks
- <risk>

## Next recommended step
<single highest value next step>

## Files to inspect next
- <path>
```

Do not hand off raw chat logs unless explicitly requested.

## 12. Tool-use rules

- Batch related reads and searches.
- Avoid repeated identical operations.
- Stop after repeated null or empty results and reassess.
- Prefer deterministic code for file writes, logging, formatting, and post-run persistence.
- Use the model only where interpretation or judgment is required.

Tool design is part of system design; unnecessary tool loops increase token cost and degrade coherence over long runs.[1]

## 13. Anti-drift rules

When the task runs long, periodically compress state into a short checkpoint with:
- current objective,
- decisions so far,
- evidence gathered,
- remaining unknowns,
- and next step.

If the context feels overloaded, do not continue blindly. Refresh from the persistent state files and resume from a clean brief. Context compaction, note-taking, and subagent isolation are standard strategies for avoiding loss of coherence in long workflows.[1]

## 14. Definition of done

A task is only done when all of the following are true:
- the requested change exists in code or docs,
- affected files are internally consistent,
- validation has been performed or explicitly deferred with reason,
- persistent state docs have been updated,
- and the next person or next session can continue without rereading the entire history.

## 15. First-session bootstrap for a brand new repo

If the repository is empty or nearly empty, create this minimum scaffold:

```text
repo/
  README.md
  .gitignore
  .env.example
  docs/
    project_brief.md
    architecture.md
    decision_log.md
    current_state.md
    next_steps.md
    handoff.md
  src/
  tests/
```

Then initialize these contents:
- `project_brief.md`: problem, users, scope, non-goals.
- `architecture.md`: first-pass system shape and module boundaries.
- `decision_log.md`: initial stack and structure decisions.
- `current_state.md`: what exists right now.
- `next_steps.md`: top 5 implementation steps.
- `handoff.md`: current bootstrap status and next action.

## 16. Preferred writing style inside the repo

Write clearly, concretely, and with low ambiguity.
- Prefer bullets over long paragraphs in operational docs.
- Prefer explicit contracts over vague intent.
- Prefer examples over abstract descriptions when defining schemas or interfaces.
- Keep markdown files easy for both humans and agents to parse.

## 17. Final behavioral rule

Every session must leave behind a cleaner context trail than it inherited.

That means:
- less ambiguity,
- better documented intent,
- clearer next steps,
- and lower dependency on hidden conversational memory.

If forced to choose between shipping one more code change and leaving durable project context, preserve the context.
