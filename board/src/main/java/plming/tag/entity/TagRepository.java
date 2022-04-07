package plming.tag.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.entity.BoardTag;

import javax.xml.bind.JAXBElement;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
