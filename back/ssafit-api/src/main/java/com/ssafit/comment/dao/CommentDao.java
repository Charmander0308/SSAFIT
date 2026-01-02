package com.ssafit.comment.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafit.comment.dto.CommentRequestDto;
import com.ssafit.comment.dto.CommentResponseDto;

@Mapper
public interface CommentDao {
	public List<CommentResponseDto> selectCommentByDomain(Map<String, Object> params);
	public CommentResponseDto selectComment(CommentRequestDto comment);
	public Integer commentCnt(Map<String, Object> params);
	public Integer insertComment(CommentRequestDto comment);
	public void updateComment(CommentRequestDto comment);
	public void deleteComment(Long commentId);
	
	public Integer findTotalCommentsById(Long id);
}
