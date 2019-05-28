package com.example.demo.comments;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.mapper.CommentMapper;
import com.example.demo.application.commands.CommentCommand;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmComment;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.services.CommentService;
import com.example.demo.commons.CommentsCommon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmCommentsServiceTest extends CommentsCommon {

	@Mock
	private FilmRepo filmRepo;

	@InjectMocks
	private CommentService commentService;

	@Mock
	private CommentMapper commentMapper;

	@Mock
	private FilmCommentsRepo filmCommentsRepo;

	@Test
	public void test() {
		Film film = getFilm();

		Mockito.when(filmRepo.getFilmDetails(Mockito.anyLong())).thenReturn(Optional.of(film));

		CommentCommand commentCommand = getCommand();

		Mockito.when(commentMapper.commentCommandToFilmCommentEntity(commentCommand.getCommentsDTO())).thenReturn(getFilmComment());
		Mockito.when(filmCommentsRepo.save(Mockito.any())).thenReturn(getFilmComment());

		commentService.addComment(commentCommand);
	}



	private CommentCommand getCommand() {
		CommentCommand commentCommand = new CommentCommand();
		commentCommand.setEntityType("FILM");

		commentCommand.setCommentsDTO(getCommentDTO());

		return commentCommand;
	}

	private CommentsDTO getCommentDTO() {
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setText(COMMENT_TEXT);
		commentsDTO.setUserId(new UserDTO());
		commentsDTO.setEntityId(FILM_ID);
		return commentsDTO;
	}


}
