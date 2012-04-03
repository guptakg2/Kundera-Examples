/*
 * Copyright 2011 Impetus Infotech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.impetus.kundera.examples.twitter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.impetus.kundera.PersistenceProperties;
import com.impetus.kundera.examples.twitter.entities.ExternalLink;
import com.impetus.kundera.examples.twitter.entities.Preference;
import com.impetus.kundera.examples.twitter.entities.Tweet;
import com.impetus.kundera.examples.twitter.entities.User;
import com.impetus.kundera.metadata.model.KunderaMetadata;
import com.impetus.kundera.metadata.model.PersistenceUnitMetadata;

/**
 * Data access object class for implementation of twitter.
 * 
 * @author amresh.singh
 */
public class TwitterService extends SuperDao implements Twitter
{
    private EntityManager em;
    private EntityManagerFactory emf;

    public TwitterService(String persistenceUnitName)
    {
        if (emf == null)
        {
            try
            {
                emf = createEntityManagerFactory(persistenceUnitName);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }     
        
    }

    public void close()
    {       
        if(emf != null) {
            emf.close();
        }
    }
    
    @Override
    public void addUser(User user)
    {
        em = emf.createEntityManager();        
        em.persist(user);
        em.close();
    }

    @Override
    public void addUser(String userId, String name, String password, String relationshipStatus)
    {
        em = emf.createEntityManager();
        User user = new User(userId, name, password, relationshipStatus);
        em.persist(user);
        em.close();
    }

    @Override
    public void savePreference(String userId, Preference preference)
    {
        em = emf.createEntityManager();
        User user = em.find(User.class, userId);
        user.setPreference(preference);
        em.persist(user);
        em.close();
    }

    @Override
    public void addExternalLink(String userId, String linkType, String linkAddress)
    {
        em = emf.createEntityManager();
        User user = em.find(User.class, userId);
        user.addExternalLink(new ExternalLink(linkType, linkAddress));

        em.persist(user);
        em.close();
    }

    @Override
    public void addTweet(String userId, String tweetBody, String device)
    {
        em = emf.createEntityManager();
        User user = em.find(User.class, userId);
        user.addTweet(new Tweet(tweetBody, device));
        em.persist(user);
        em.close();
    }

    @Override
    public void startFollowing(String userId, String friendUserId)
    {
        em = emf.createEntityManager();
        User user = em.find(User.class, userId);
        User friend = em.find(User.class, friendUserId);

        user.addFriend(friend);
        em.persist(user);

        friend.addFollower(user);
        em.persist(friend);
        em.close();
    }

    @Override
    public void addFollower(String userId, String followerUserId)
    {
        em = emf.createEntityManager();
        User user = em.find(User.class, userId);
        User follower = em.find(User.class, followerUserId);

        user.addFollower(follower);
        em.persist(user);
        em.close();
    }
    
    
    

    @Override
	public List<User> getAllUsers() {
    	em = emf.createEntityManager();
        Query q = em.createQuery("select u from User u");
        
        List<User> users = q.getResultList();
        em.close();
        
        return users;
	}

	@Override
    public List<Tweet> getAllTweets(String userId)
    {
        em = emf.createEntityManager();
        Query q = em.createQuery("select u from User u where u.USER_ID =:userId");
        q.setParameter("userId", userId);
        List<User> users = q.getResultList();
        em.close();
        if (users == null || users.isEmpty())
        {
            return null;
        }
        else
        {
            return users.get(0).getTweets();
        }
    }

    @Override
    public List<User> getFollowers(String userId)
    {
        em = emf.createEntityManager();
        Query q = em.createQuery("select u from User u where u.USER_ID =:userId");
        q.setParameter("userId", userId);
        List<User> users = q.getResultList();
        em.close();
        if (users == null || users.isEmpty())
        {
            return null;
        }
        return users.get(0).getFollowers();
    }

    @Override
    public List<Tweet> findTweetByBody(String tweetBody)
    {
        em = emf.createEntityManager();
        Query q = em.createQuery("select u.body from User u where u.body like :body");
        q.setParameter("body", tweetBody);
        List<Tweet> tweets = q.getResultList();
        em.close();
        return tweets;
    }

    @Override
    public List<Tweet> findTweetByDevice(String deviceName)
    {
        em = emf.createEntityManager();
        Query q = em.createQuery("select u.device from User u where u.device like :device");
        q.setParameter("device", deviceName);
        List<Tweet> tweets = q.getResultList();
        em.close();
        return tweets;
    }
}
