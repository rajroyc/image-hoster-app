package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    //This controller method is called when the request is made to the path /image/{imageId}/{imageTitle}/comments
    //This method will capture the comment text from the request, add the local date, map the logged-in user to the comment,
    //map the image to the comment and then call the service to persist the Comment.
    //This method return the page at /images/{id}/{title}
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@PathVariable("imageId") Integer id, @PathVariable("imageTitle") String title, Comment comment, HttpSession httpSession) {
        Image updatedImage = imageService.getImage(id);

        comment.setImage(updatedImage);
        comment.setUser((User) httpSession.getAttribute("loggeduser"));
        comment.setCreatedDate(LocalDate.now());
        commentService.createComment(comment);

        return "redirect:/images/" + id + "/" + title;
    }
}
