package org.wildcodeschool.myblog.mapper;

import org.springframework.stereotype.Component;
import org.wildcodeschool.myblog.dto.ArticleCreateDTO;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.model.Category;
import org.wildcodeschool.myblog.model.Image;
import org.wildcodeschool.myblog.model.ArticleAuthor;
import org.wildcodeschool.myblog.model.Author;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    public ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());

        if (article.getCategory() != null) {
            articleDTO.setCategoryName(article.getCategory().getName());
        }

        if (article.getImages() != null) {
            articleDTO.setImageUrls(article.getImages().stream()
                    .map(Image::getUrl)
                    .collect(Collectors.toList()));
        }

        if (article.getArticleAuthors() != null) {
            articleDTO.setAuthors(article.getArticleAuthors().stream()
                    .map(articleAuthor -> {
                        AuthorDTO authorDTO = new AuthorDTO();
                        authorDTO.setId(articleAuthor.getAuthor().getId());
                        authorDTO.setFirstName(articleAuthor.getAuthor().getFirstname());
                        authorDTO.setLastName(articleAuthor.getAuthor().getLastname());
                        return authorDTO;
                    })
                    .collect(Collectors.toList()));
        }

        return articleDTO;
    }

    public Article convertToEntity(ArticleCreateDTO articleCreateDTO) {
        Article article = new Article();
        article.setTitle(articleCreateDTO.getTitle());
        article.setContent(articleCreateDTO.getContent());

        if (articleCreateDTO.getCategoryId() != null) {
            Category category = new Category();
            category.setId(articleCreateDTO.getCategoryId());
            article.setCategory(category);
        }

        if (articleCreateDTO.getImages() != null) {
            List<Image> images = articleCreateDTO.getImages().stream()
                    .map(imageDTO -> {
                        Image image = new Image();
                        image.setUrl(imageDTO.getUrl());
                        return image;
                    })
                    .collect(Collectors.toList());
            article.setImages(images);
        }

        return article;
    }
}
