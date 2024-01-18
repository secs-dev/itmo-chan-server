WITH Pics as (
    SELECT * FROM "Picture_attachments"
    LEFT JOIN "Pictures" 
      ON "Pictures".picture_id = "Picture_attachments".picture_id
    WHERE "Picture_attachments".comment_id = 7
), Vids as (
    SELECT * FROM "Video_attachments"
    LEFT JOIN "Videos" 
      ON "Videos".video_id = "Video_attachments".video_id
    WHERE "Video_attachments".comment_id = 7
)
SELECT * FROM "Comments"
LEFT JOIN "Reaction_sets" 
  ON "Comments".reactions_id = "Reaction_sets".r_set_id
LEFT JOIN Pics 
  ON "Comments".comment_id = Pics.comment_id
LEFT JOIN Vids 
  ON "Comments".comment_id = Vids.comment_id
WHERE "Comments".comment_id = 7;
