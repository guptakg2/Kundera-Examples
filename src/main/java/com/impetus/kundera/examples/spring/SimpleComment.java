/*******************************************************************************
 * * Copyright 2012 Impetus Infotech.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 ******************************************************************************/
package com.impetus.kundera.examples.spring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class for Simple comment
 * @author amresh.singh
 *
 */

@Entity
@Table(name="SIMPLE_COMMENT", schema="KunderaExamples@twissandra")
@XmlRootElement(name = "SimpleComment")
public class SimpleComment
{
    @Id
    @Column(name = "COMMENT_ID")
    private int id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "COMMENT_TEXT")
    private String commentText;

    enum Day
    {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
    
    @Column(name = "DAY_OF_COMMENT")
    Day dayOfComment;   
    
    
    public SimpleComment() {
    }
    
    public SimpleComment(int commentId, String userName, String commentText, Day dayOfComment) {
        this.id = commentId;
        this.userName = userName;
        this.commentText = commentText;
        this.dayOfComment = dayOfComment;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }   

    /**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
     * @return the commentText
     */
    public String getCommentText()
    {
        return commentText;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setCommentText(String commentText)
    {
        this.commentText = commentText;
    }

	/**
	 * @return the dayOfComment
	 */
	public Day getDayOfComment() {
		return dayOfComment;
	}

	/**
	 * @param dayOfComment the dayOfComment to set
	 */
	public void setDayOfComment(Day dayOfComment) {
		this.dayOfComment = dayOfComment;
	}  

}
