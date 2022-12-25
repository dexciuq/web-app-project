# web-app-project

## Entity-Relationship diagram (ERD):

![NoSQL - Database ER diagram](https://user-images.githubusercontent.com/85491176/209480469-4b5274cc-ed02-4bcb-89bd-9cd0986aaea7.png)

## Collection-Relationship Diagram (CRD):

![NoSQL - Collection ER diagram](https://user-images.githubusercontent.com/85491176/209480471-7141fb10-3cd5-414a-9950-be6b8eb3ba6a.png)

## Workload

_Workload: List Queries_
1. _Registration_ – write – The data from the registration form is sent to the server and checked for the correctness of the written data.
2. _Authorization_ – read – Encrypted data from the server is checked against the user's entered data. If the data is correct, then login to the user's personal account is performed.
3. _Follow_ – write – The ability to search, and follow users and send data to the database.
4. _View the list of following users_ – read – The user will receive a list of following users from the database and go to their pages.
5. _Loading the news feed (uploading photos and videos)_ – read – following users' posts will be visible in the news feed.
6. _Ability to see likes and comments_ – read – The user will be able to receive information about likes and comments from the database
7. _The ability to add to favorites, comment_ – write – The ability for the user to like and leave comments.

_Workload: quantify/qualify the queries_
1. _Registration_. Since this is a training project, we do not expect a large influx of users. 20 requests per day will be enough to test the registration. 20 writes/day. Since the request is sent to the database, the operation should not take more than 1 second.
2. _Authorization_. The situation with authorization is similar. 20 records per day will be enough for testing. Since a get request is used, the operation should not take longer than 1 second.
3. _Follow_. It is expected from 1 to 50 post requests per day. Since this is a post request with a filter, the operation should not take longer than 1 second. Then the following user is sent to the database, which also should not last longer than 1 second.
4. _View the list of following users_. No more than 20 requests per day. There is a get requests for all the data, but since we will have no more than a hundred following users, the request and the render should be no more than 3 seconds.
5. _Loading the news feed_. No more than 2 requests per day. Since it will be necessary to upload not only text, but also photos, rendering can take up to 3 seconds per post.
6._ Ability to see likes and comments_. Approximately 20 requests per day will be enough for testing. Since this is a get request, the operation should take no longer than 1 second.
7. _The ability to add to favorites, comment_. As usual, 20 requests will be enough. Since this is an addition to the database, the operation should take no longer than 1 second.

## Relationship models

### User model

```
User {
  _id
  UserRole
  Username
  Email
  PhoneNumber
  Password
  RegistrationDate
  UserProfile (Embedded Document) {
    FirstName
    LastName
    MiddleName (Optional)
    DOB
    Address
    Degree
    AboutMe
    ProfilePictureURL
  }
  Followers (Reference Document) {
    _id (User1)
    _id (User2)
    ...
  }
  Following (Reference Document) {
    _id (User1)
    _id (User2)
    ...
  }
  Posts (Reference Document) {
    _id (Post1)
    _id (Post2)
    ...
  }
}
```

### Post model

```
Post {
  _id
  Title
  Description
  ImageURL (Optional)
  Comments (Array of Embedded Documents) []
  Tags []
  LikeCount
  CreationDate
}
```

### Comment model

```
Comment {
  _id
  Comments (Array of Embedded Documents) []
  User_ID (Reference Document)
  Text
  LikeCount
  CreationDate
}
```
