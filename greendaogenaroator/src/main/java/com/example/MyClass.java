package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) throws Exception {

        //place where db folder will be created inside the project folder
        Schema schema = new Schema(1, "database");



        Entity week = schema.addEntity("Week");

        Property week_id = week.addIdProperty().autoincrement().getProperty(); //It is the primary key for uniquely identifying a row

//        week.addLongProperty("week_id");

        week.addIntProperty("week_number").notNull();  //Not null is SQL constrain

        week.addIntProperty("progress").notNull();

        week.addBooleanProperty("locked");

        week.addIntProperty("rate");




        Entity date = schema.addEntity("Date");

        date.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        date.addIntProperty("day_number").notNull();  //Not null is SQL constrain

        date.addLongProperty("week_id");

        date.addBooleanProperty("locked");

        date.addIntProperty("progress");

//        date.addToOne(week , week_id , "week_id" );



//        Entity customer  = schema.addEntity("Customer");
//        customer.addLongProperty("CustomerId").primaryKey();
//        customer.addStringProperty("Name").notNull();
//
//
//        Entity order  = schema.addEntity("Order");
//        order.addLongProperty("OrderId").primaryKey();
//        order.addDoubleProperty("Money").notNull();
//
//        // establish one to many Association (customers on orders for one to many)
//        Property property = order.addLongProperty("CustomerId").getProperty();
//        order.addToOne(customer, property);
//        customer.addToMany(order, property).setName("Orders");


        //Entity i.e. Class to be stored in the database // ie table LOG
        Entity user_added_vocabulary = schema.addEntity("UserAddedVocabulary");

        user_added_vocabulary.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        user_added_vocabulary.addStringProperty("word_eng").notNull();  //Not null is SQL constrain

        user_added_vocabulary.addStringProperty("word_per").notNull();

        user_added_vocabulary.addStringProperty("example");

        user_added_vocabulary.addStringProperty("image_address");

        user_added_vocabulary.addIntProperty("stage").notNull();

        user_added_vocabulary.addLongProperty("date_id").notNull();

        user_added_vocabulary.addLongProperty("word_id").notNull();

        user_added_vocabulary.addLongProperty("last_reviewed_time").notNull();



        Entity general = schema.addEntity("General");

        general.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        general.addIntProperty("general_progress");  //Not null is SQL constrain

        general.addLongProperty("current_active_date_id");

        general.addLongProperty("current_active_week_id");




        Entity idiom = schema.addEntity("Idiom");

        idiom.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        idiom.addStringProperty("idiom_eng");  //Not null is SQL constrain

        idiom.addStringProperty("idiom_per");

        idiom.addStringProperty("idiom_example_eng");  //Not null is SQL constrain

        idiom.addStringProperty("idiom_example_per");

        idiom.addLongProperty("date_id");




        Entity review = schema.addEntity("Review");

        review.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        review.addLongProperty("week_id").notNull();

        review.addLongProperty("date_id");

        review.addStringProperty("word");  //Not null is SQL constrain

        review.addStringProperty("definition");




        Entity word_search = schema.addEntity("WordSearch");

        word_search.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        word_search.addLongProperty("week_id").notNull();

        word_search.addLongProperty("date_id");

        word_search.addIntProperty("paragraph_number");  //Not null is SQL constrain

        word_search.addStringProperty("paragraph_eng");

        word_search.addStringProperty("paragraph_per");



        Entity word_search_deleted_word = schema.addEntity("WordSearchDeletedWord");

        word_search_deleted_word.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        word_search_deleted_word.addLongProperty("word_search_id").notNull();

        word_search_deleted_word.addIntProperty("paragraph_number");

        word_search_deleted_word.addStringProperty("deleted_word");

        word_search_deleted_word.addStringProperty("hint");




        Entity main_context = schema.addEntity("MainContext");

        main_context.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        main_context.addLongProperty("date_id").notNull();

        main_context.addStringProperty("title_eng");

        main_context.addStringProperty("title_per");

        main_context.addStringProperty("context_eng");

        main_context.addStringProperty("context_per");




        Entity pic = schema.addEntity("Pic");

        pic.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        pic.addLongProperty("word_id").notNull();

        pic.addStringProperty("file_name");




        Entity pronunciation = schema.addEntity("Pronunciation");

        pronunciation.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        pronunciation.addLongProperty("word_id").notNull();

        pronunciation.addStringProperty("file_name");



        Entity synonym = schema.addEntity("Synonym");

        synonym.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        synonym.addLongProperty("word_id").notNull();

        synonym.addStringProperty("file_name");




        Entity antonym = schema.addEntity("Antonym");

        antonym.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        antonym.addLongProperty("word_id").notNull();

        antonym.addStringProperty("file_name");



        Entity word_form = schema.addEntity("WordForm");

        word_form.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        word_form.addLongProperty("word_id").notNull();

        word_form.addBooleanProperty("is_noun");

        word_form.addBooleanProperty("is_adv");

        word_form.addBooleanProperty("is_adj");

        word_form.addBooleanProperty("is_verb");

        word_form.addStringProperty("word");

        word_form.addStringProperty("pronunciation_file_address");




        Entity sentence = schema.addEntity("Sentence");

        sentence.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        sentence.addLongProperty("word_id").notNull();

        sentence.addStringProperty("sentence_eng");

        sentence.addStringProperty("sentence_per");

        sentence.addStringProperty("pronunciation_file_address");

        sentence.addStringProperty("deleted_word");



        Entity user_record = schema.addEntity("UserRecord");

        user_record.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        user_record.addLongProperty("word_id").notNull();

        user_record.addLongProperty("date_id").notNull();

        user_record.addBooleanProperty("is_speaking");

        user_record.addBooleanProperty("is_reading");

        user_record.addBooleanProperty("is_writing");

        user_record.addBooleanProperty("is_listening");

        user_record.addBooleanProperty("score");




        Entity word = schema.addEntity("Word");

        word.addIdProperty().autoincrement(); //It is the primary key for uniquely identifying a row

        word.addLongProperty("week_id").notNull();

        word.addLongProperty("date_id").notNull();

        word.addStringProperty("word");

        word.addStringProperty("per_def");

        word.addStringProperty("eng_def");





        //  ./app/src/main/java/   ----   com/codekrypt/greendao/db is the full path
        new DaoGenerator().generateAll(schema, "./app/src/main/java");

    }

}
