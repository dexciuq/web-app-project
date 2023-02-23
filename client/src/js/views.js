const UI_ELEMENTS = {
    SEARCH_FORM: document.querySelector('#search-form'),
    SEARCH_INPUT: document.querySelector('#search'),

    RECOMMENDATIONS_LIST: document.querySelector('.recommendations-list'),

    USERS_PAGE: document.querySelector('#users-page'),
    USERS_LIST: document.querySelector('#users-list'),
    USERS_RECOMMENDATIONS: document.querySelector('#recommendations-users'),
    USER_PROFILE_TITLE: document.querySelector('.user-profile'),

    PROFILE_PAGE: document.querySelector('#profile-section'),
    PROFILE_AVATAR: document.querySelector('#profile-avatar-img'),
    PROFILE_USERNAME: document.querySelector('.profile-info-user'),
    PROFILE_EMAIL: document.querySelector('.profile-info-email'),
    PROFILE_REGISTRATION_DATE: document.querySelector('.profile-info-date'),
    PROFILE_FOLLOWING: document.querySelector('.profile-info-following'),
    PROFILE_FOLLOWERS: document.querySelector('.profile-info-followers-count'),
    PROFILE_BUTTON: document.querySelector('#profile-button'),

    HOME_BUTTON: document.querySelector('#home-button'),

    MAIN_USER_NEWS_FEED: document.querySelector('#main-user-news-feed'),

    AUTHORIZATION_SECTION: document.querySelector('#authorization-section'),
    FULLSCREEN_SECTION: document.querySelector('.fullscreen-container'),
    PERSONAL_PROFILE_PAGE: document.querySelector('.personal-profile-wrapper'),

    REGISTER_FORM: document.querySelector('#register-form'),
    REGISTER_USERNAME: document.querySelector('#username-reg'),
    REGISTER_EMAIL: document.querySelector('#email-reg'),
    REGISTER_PASSWORD: document.querySelector('#password-reg'),
    REGISTER_NAME: document.querySelector('#name-reg'),
    REGISTER_SURNAME:document.querySelector('#surname-reg'),
    REGISTER_DOB: document.querySelector('#dob-reg'),
    REGISTER_NUMBER: document.querySelector('#phoneNumber-reg'),

    SIGN_IN_FORM: document.querySelector('#sign-in-form'),
    SIGN_IN_USERNAME: document.querySelector('#username-login'),
    SIGN_IN_PASSWORD: document.querySelector('#username-password'),

    POST_BLOCK_AVATAR: document.querySelector('#post-block-avatar'),
    POST_BLOCK_FORM: document.querySelector('#post-block-form'),
    SEND_POST_INPUT: document.querySelector('#input-post-send'),

    POSTS_SECTION: document.querySelector('#posts-section'),
    POSTS_CONTAINER: document.querySelector('#news-page-template'),
    POST_TEMPLATE: document.querySelector('#single-post-template'),
    ALL_POSTS_FEED: document.querySelector('#all-posts-feed'),

    LOGOUT_BUTTON: document.querySelector('#logout-btn'),


    BETA_BUTTON: document.querySelector('#beta-btn'),
    BETA_CONTAINER: document.querySelector('.beta-wrapper'),

    GET_USER_BY_ID_FORM: document.querySelector('#get-user-by-id-form'),
    GET_USER_BY_ID_INPUT: document.querySelector('#get-user-by-id-input'),

    DELETE_USER_BY_ID_FORM: document.querySelector('#delete-user-by-id-form'),
    DELETE_USER_BY_ID_INPUT: document.querySelector('#delete-user-by-id-input'),

    FOLLOW_USER_FORM: document.querySelector('#follow-user-form'),
    FOLLOW_USER_INPUT: document.querySelector('#follow-user-by-username'),

    UNFOLLOW_USER_FORM: document.querySelector('#unfollow-user-form'),
    UNFOLLOW_USER_INPUT: document.querySelector('#unfollow-user-by-username'),

    SEARCH_POST_FORM: document.querySelector('#search-post-form'),
    SEARCH_FORM_INPUT_TAG: document.querySelector('#seach-post-tag'),
    SEARCH_FORM_INPUT_TITLE: document.querySelector('#search-post-title'),

    GET_POST_BY_ID_FORM: document.querySelector('#get-post-by-id-form'),
    GET_POST_BY_ID_INPUT: document.querySelector('#get-post-by-id-input'),

    UPDATE_POST_BY_ID_FORM: document.querySelector('#update-post-by-id-form'),
    UPDATE_POST_BY_ID_INPUT: document.querySelector('#update-post-by-id-input'),

    DELETE_POST_BY_ID_FORM: document.querySelector('#delete-post-by-id-form'),
    DELETE_POST_BY_ID_INPUT: document.querySelector('#delete-post-by-id-input'),

    POST_COMMENT_FORM: document.querySelector('#post-comment-form'),
    POST_COMMENT_INPUT: document.querySelector('#post-comment-input'),
    POST_COMMENT_INPUT_COMMENT: document.querySelector('#post-comment-input-comment'),

    UPDATE_COMMENT_FORM: document.querySelector('#update-comment-form'),
    UPDATE_COMMENT_INPUT: document.querySelector('#update-comment-input'),
    UPDATE_COMMENT_INPUT_COMMENT:document.querySelector('#update-comment-input-comment'),

    DELETE_COMMENT_FORM: document.querySelector('#delete-comment-form'),
    DELETE_COMMENT_INPUT: document.querySelector('#delete-comment-input'),
    DELETE_COMMENT_INPUT_COMMENT: document.querySelector('#delete-comment-input-comment'),

}    

export { UI_ELEMENTS }