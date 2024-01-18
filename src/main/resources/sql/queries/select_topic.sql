SELECT 
	c.thread_id,
	popularity,
	comment_id,
	title,
	content,
	reactions_id,
	creation_date,
	trashed
FROM "Topics" top
JOIN "Threads" thr
  ON thr.topic_id = top.topic_id
JOIN "Comments" c
  ON thr.init_comment_id = c.comment_id
WHERE c.deleted = FALSE
  AND top.topic_id = 1;