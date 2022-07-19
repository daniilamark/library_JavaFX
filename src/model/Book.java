package model;

// класс описывающий книгу, модель данных
public class Book {
    private int id; // код
    private String title; // название
    private String author; // автор
    private int year; // год выпуска книги
    private int pages; // кол-во страниц

    // конструктор класса
    public Book(int id, String title, String author, int year, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    // методы получения данных мз модели
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }
}
