-- sprzedaż słupa search_words,search_matches, posts, users 
create or replace view post_with_word1 as
		SELECT post_id 
		FROM search_matches sm
		join(
			select id
			FROM search_words
            Where word like '%słup%'or word like '%konto bankow%') word1
		on sm.word_id = word1.id;
        
create or replace view post_with_word2 as
		SELECT post_id 
		FROM search_matches sm
		join(
			select id
			FROM search_words
            Where word like '%kupi%' or word like '%sprzed%') word2
		on sm.word_id = word2.id;

--  wyszukiwanie na forach sprzedam kupie, forum dane dokommenty, oszustwa
create or replace view post_in_forums_kupie_sprzedam as
select p.id as post_id
from posts p
join
	(
    select t.id as topic_id
	from topics t
	where t.forum_id = 6 -- sprzedam
    or t.forum_id = 7  -- kupie
    or t.forum_id = 20 -- dane dokumenty 
	or t.forum_id = 35 -- oszustwa employees
    ) t_id
on p.topic_id = t_id.topic_id;
        
select posts.*, u.email,u.location, u.realname
from users u
JOIN(
	SELECT poster_id, poster, message
	FROM posts p
	join(
		select distinct pww1.post_id
		from post_with_word1 pww1
		join(
			select * 
			from post_with_word2
			) pww2
		on pww1.post_id=pww2.post_id
		) post
	on p.id = post.post_id
	order by p.id) posts
on u.id = posts.poster_id;
        
select u.email,u.location, u.realname, posts.* 
from users u
JOIN(
	SELECT poster_id, poster, message
	FROM posts p
	join(
		select distinct pww1.post_id
		from post_with_word1 pww1
		join(
			select distinct pw2.post_id
			from post_with_word2 pw2
            join
				(
                select pifks.post_id as post_id_1
                from post_in_forums_kupie_sprzedam pifks
                ) t
			on t.post_id_1 = pw2.post_id
			) pww2
		on pww1.post_id=pww2.post_id
		) post
	on p.id = post.post_id
	order by p.id) posts
on u.id = posts.poster_id;
        
-- zlecenie zrobienia bomby lu mordersta
create or replace view bomba_morderstwo as
		SELECT post_id 
		FROM search_matches sm
		join(
			select id
			FROM search_words
            Where word like '%bomb%'or word like '%morderstw%') word1
		on sm.word_id = word1.id;
        
create or replace view zlecenie as
		SELECT post_id 
		FROM search_matches sm
		join(
			select id
			FROM search_words
            Where word like '%zlece%' or word like '%wynaj%' ) word2
		on sm.word_id = word2.id;

-- zlecenie bomba
select posts.*, u.email,u.location, u.realname
from users u
JOIN(
	SELECT poster_id, poster, message
	FROM posts p
	join(
		select z.post_id
		from zlecenie z
		join(
			select * 
			from bomba_morderstwo
			) bm
		on bm.post_id=z.post_id
		) post
	on p.id = post.post_id
	order by p.id) posts
on u.id = posts.poster_id;

-- bomba
select posts.*, u.email,u.location, u.realname
from users u
JOIN(
	SELECT poster_id, poster, message
	FROM posts p
	join(
		select * 
		from bomba_morderstwo
		) post
	on p.id = post.post_id
	order by p.id) posts
on u.id = posts.poster_id;

-- zlecenie
select posts.*, u.email,u.location, u.realname
from users u
JOIN(
	SELECT poster_id, poster, message
	FROM posts p
	join(
		select * 
		from zlecenie
		) post
	on p.id = post.post_id
	order by p.id) posts
on u.id = posts.poster_id;


-- najbardziej  aktywni użytkownicy w danych latach
select most_active_user.*, u.*
-- u.email,u.location, u.realname, u.title, u.signature, u.reputation
from users u
JOIN(
	SELECT poster_id, year(FROM_UNIXTIME(posted)) as posted, count(poster_id) as count_of_post
	FROM posts p
	JOIN (
		SELECT id 
		FROM forum.users
		Where group_id = 8
        ) active_user
	on p.poster_id = active_user.id
	group by year(FROM_UNIXTIME(posted)), poster_id
	order by year(FROM_UNIXTIME(posted)), count_of_post desc
    ) most_active_user
on u.id = most_active_user.poster_id
group by posted;

-- aktywni użytkownicy
select count(id) 
from users
where group_id = 8;

-- uzytkownicy którzy nie napisali zadnego postu
select * 
from users u
where id NOT IN
	(select poster_id from posts);
    

-- 5 z najdłuzszymi komentarzami

select u.username, all_length 
from users u
join(
	select poster_id, sum(length(message)) as all_length
	from posts
    where poster_id != 1 
	group by poster_id
	order by all_length desc
	limit 5) posters
on u.id = posters.poster_id
;



 
