package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private Map<Long, Post> map = new ConcurrentHashMap<>();
  private AtomicLong idCounter = new AtomicLong(1);


  public List<Post> all() {
    return new ArrayList<>(map.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(map.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      map.put(idCounter.get(), post);
      post.setId(idCounter.getAndIncrement());
    } else if (!map.containsKey(post.getId())) {
      throw new NotFoundException(String.format("There is not post with %d", post.getId()));
    } else {
      map.put(post.getId(), post);
    }
    return post;
  }

  public void removeById(long id) {
    map.remove(id);
  }
}
