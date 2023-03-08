# Digletto

## What to do?

### Problems

- [ ] in tests, I tried to check `createdDate`, but `write...AsString()` serializes differently than application.
  Application rounds millis. When this problem is solved, custom `ODTMatcher`.

### Architecture

I would like to have packages:

- core - self-sufficient. Does not use anything on its own, perhaps event framework.
- web - dependent on `core`, this would be package to communicate with the outside world.

### Tasks

<details>
<summary> [ ] Liquibase</summary>

- add Liquibase

</details>

<details>
<summary> [ ] Exception handling</summary>

- should throw exceptions with multiple causes
- one cause for most cases
- multiple for validation exceptions
- works on error codes - let client implement concrete messages

</details>

<details>
  <summary> [ ] Idea crud</summary>

- [ ] create
- creates new idea and persists it in database
- requires name and owning user id
- names unique per user

---

- [ ] listing
- returns pageable and filterable list of ideas
- idea specified as:
    - id
    - creation date
    - last activity date
    - name
    - owner id
    - average rating (0.0 - 5.0) - mostly to mess with database views
    - status

---

- [ ] get details
- returns single idea object
- idea specified as:
    - id
    - creation date
    - last activity date
    - name
    - description
    - owner id
    - list of involved users with last activity dates
    - average rating (0.0 - 5.0)
    - list of ratings by users
    - status

--- 

- [ ] update
- update fields of the idea
- if idea does not exist, throw 404
- only in status DRAFT - otherwise throw 403
- if nothing changed or the same data - do nothing and return 200

---

- [ ] delete
- deletes idea
- if idea does not exist, throw 404
- only in status DRAFT - otherwise throw 403

</details>

<details>
<summary> [ ] Event notifications</summary>

- [ ] create idea event
- [ ] delete idea event
- [ ] update idea event
- [ ] change state idea event

</details>
