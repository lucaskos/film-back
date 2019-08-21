package com.example.demo.comments;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.mapper.CommentMapper;
import com.example.demo.application.commands.CommentCommand;
import com.example.demo.application.model.Film;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.services.CommentService;
import com.example.demo.commons.CommentsCommon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

		Mockito.when(commentMapper.commentCommandToFilmCommentEntity(Mockito.any())).thenReturn(getFilmComment());
		Mockito.when(filmCommentsRepo.save(Mockito.any())).thenReturn(getFilmComment());
		Mockito.when(filmRepo.save(Mockito.any())).thenReturn(getFilm());

		commentService.addComment(getFilmCommentDTO());

		Assert.assertNotNull(film.getFilmComments().get(0));
	}



	private CommentCommand getFilmCommand() {
		CommentCommand commentCommand = new CommentCommand();
		commentCommand.setEntityType("FILM");

		commentCommand.setCommentsDTO(getFilmCommentDTO());

		return commentCommand;
	}

	private CommentCommand getPersonCommand() {
		CommentCommand commentCommand = new CommentCommand();
		commentCommand.setEntityType("PERSON");

		commentCommand.setCommentsDTO(getPersonCommentDTO());

		return commentCommand;
	}

	private CommentsDTO getPersonCommentDTO() {
		CommentsDTO commentsDTO = new CommentsDTO();

		return commentsDTO;
	}

	private CommentsDTO getFilmCommentDTO() {
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setText(COMMENT_TEXT);
		commentsDTO.setUserId(new UserDTO());
		commentsDTO.setEntityId(FILM_ID);
		commentsDTO.setEntityType("FILM");
		return commentsDTO;
	}


}
