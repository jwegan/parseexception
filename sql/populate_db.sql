USE parseexceptiondb;

-- *********************************************************************
-- Create users
-- *********************************************************************
INSERT INTO users (username, is_mod, oid_identity, oid_server)
VALUES ('jegan', true, 'x', 'x');

INSERT INTO users (username, oid_identity, oid_server)
VALUES ('user2', 'x', 'x');

INSERT INTO users (username, oid_identity, oid_server)
VALUES ('user3', 'x', 'x');

-- *********************************************************************
-- Create friends
-- *********************************************************************
INSERT INTO friends (uid, friend_id)
SELECT u1.uid, u2.uid
FROM users u1, users u2
WHERE u1.username = 'jegan' AND u2.username = 'user2';

INSERT INTO friends (uid, friend_id)
SELECT u1.uid, u2.uid
FROM users u1, users u2
WHERE u1.username = 'user2' AND u2.username = 'user3';

INSERT INTO friends (uid, friend_id)
SELECT u1.uid, u2.uid
FROM users u1, users u2
WHERE u1.username = 'user3' AND u2.username = 'jegan';

-- *********************************************************************
-- Create solutions
-- *********************************************************************
INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'My cool solution', 'rm -rf *', 'rm -rf *'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'Another cool solution', '1 + 1 = 2', '1 + 1 = 2'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'How to do something', 'Err... nvm', 'Err... nvm'
FROM users u
WHERE u.username = 'user2';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'Hello World', 'My solution', 'My solution'
FROM users u
WHERE u.username = 'user3';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution1', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution2', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution3', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution4', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution5', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution6', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution7', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution8', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution9', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution10', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution11', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution12', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution13', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution14', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution15', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution16', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution17', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution18', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution19', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution20', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution21', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution22', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution23', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution24', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

INSERT INTO solutions (submitter, question, answer, answer_raw)
SELECT u.uid, 'solution25', 'this is a solution', 'this is a solution'
FROM users u
WHERE u.username = 'jegan';

-- *********************************************************************
-- Create tags
-- *********************************************************************
INSERT INTO tags (sid, tag)
SELECT s.sid, 'unremarkable'
FROM solutions s
WHERE s.question = 'My cool solution';

INSERT INTO tags (sid, tag)
SELECT s.sid, 'road'
FROM solutions s
WHERE s.question = 'My cool solution';

INSERT INTO tags (sid, tag)
SELECT s.sid, 'inspiring'
FROM solutions s
WHERE s.question = 'Another cool solution';

INSERT INTO tags (sid, tag)
SELECT s.sid, 'unremarkable'
FROM solutions s
WHERE s.question = 'This one time at band camp';

INSERT INTO tags (sid, tag)
SELECT s.sid, 'unremarkable'
FROM solutions s
WHERE s.question = 'Hello World';

INSERT INTO tags (sid, tag)
SELECT s.sid, 'hello'
FROM solutions s
WHERE s.question = 'Hello World';

-- *********************************************************************
-- Create votes
-- *********************************************************************
INSERT INTO votes (uid, sid, upvote)
SELECT u.uid, s.sid, '1'
FROM users u, solutions s
WHERE u.username = 'jegan' AND s.submitter = u.uid;

INSERT INTO votes (uid, sid, upvote)
SELECT u.uid, s.sid, '1'
FROM users u, solutions s
WHERE u.username = 'user2' AND s.submitter = u.uid;

INSERT INTO votes (uid, sid, upvote)
SELECT u.uid, s.sid, '1'
FROM users u, solutions s
WHERE u.username = 'user3' AND s.submitter = u.uid;

INSERT INTO votes (uid, sid, upvote)
SELECT u.uid, s.sid, '1'
FROM users u, users u2, solutions s
WHERE u.username = 'jegan' AND u2.username = 'user2' 
	AND s.submitter = u2.uid;

INSERT INTO votes (uid, sid, upvote)
SELECT u.uid, s.sid, '3'
FROM users u, users u2, solutions s
WHERE u.username = 'user3' AND u2.username = 'user2' 
	AND s.submitter = u2.uid;

INSERT INTO votes (uid, sid, upvote)
SELECT u.uid, s.sid, '2'
FROM users u, solutions s
WHERE u.username = 'jegan' AND s.submitter <> u.uid;

UPDATE solutions SET score = 1 WHERE sid = 1;
UPDATE solutions SET score = 1 WHERE sid = 2;
UPDATE solutions SET score = 2 WHERE sid = 3;
UPDATE solutions SET score = 0 WHERE sid = 4;

-- *********************************************************************
-- Message Table
-- *********************************************************************
INSERT INTO messages (from_id, to_id, subject, body)
SELECT u1.uid, u2.uid, 'Hello from user2', 'Hi!'
FROM users u1, users u2
WHERE u1.username = 'user2' AND u2.username <> 'user2';

INSERT INTO messages (from_id, to_id, subject, body, unread, threadid)
SELECT u1.uid, u2.uid, 'Hello from user3', 'Hi!', FALSE, 3
FROM users u1, users u2
WHERE u1.username = 'user3' AND u2.username = 'jegan';

INSERT INTO messages (from_id, to_id, subject, body, threadid)
SELECT u1.uid, u2.uid, 'RE: Hello from user3', 'Hello to you back', 3
FROM users u1, users u2
WHERE u1.username = 'jegan' AND u2.username = 'user3';

-- *********************************************************************
-- Comments Table
-- *********************************************************************
INSERT INTO comments (uid, sid, body, body_raw, path)
SELECT u.uid, s.sid, '<p>Was it hard to figure out?</p>', 'Was it hard to figure out?', '1.'
FROM users u, solutions s
WHERE u.username = 'user2' and s.question = 'Another cool solution';

INSERT INTO comments (uid, sid, body, body_raw, path)
SELECT u.uid, s.sid, '<p>Not really</p>', 'Not really', '1.2.'
FROM users u, solutions s
WHERE u.username = 'jegan' and s.question = 'Another cool solution';

INSERT INTO comments (uid, sid, body, body_raw, path, editted)
SELECT u.uid, s.sid, '<p>Cool site</p>', 'Cool site', '3.', true
FROM users u, solutions s
WHERE u.username = 'user3' and s.question = 'Another cool solution';

INSERT INTO comments (uid, sid, body, body_raw, path)
SELECT u.uid, s.sid, '<p>hello world?</p>', 'hello world?', '1.2.4.'
FROM users u, solutions s
WHERE u.username = 'user2' and s.question = 'Another cool solution';

INSERT INTO comments (uid, sid, body, body_raw, path)
SELECT u.uid, s.sid, '<p>Yep</p>', 'Yep', '3.5.'
FROM users u, solutions s
WHERE u.username = 'user2' and s.question = 'Another cool solution';

-- *********************************************************************
-- Settings Table
-- *********************************************************************
INSERT INTO settings (uid)
SELECT uid FROM users;


COMMIT;



