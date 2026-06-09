
# Twitter Clone Backend & Frontend

> **Status:** This project is now **complete**. All planned features have been implemented.

This is a **modulith-architecture based Twitter clone** built with **Spring Boot**, **Java**, **React**, **HashiCorp Consul** for service discovery, and **NeonDB (PostgreSQL)** as the database.  
It supports real-time interactions using **WebSockets** (for notifications) and includes advanced features like image cropping, open-feign clients, MySQL triggers (initially), and a fully functional timeline system.

---

## Tech Stack
- **Backend**: Spring Boot (Java), WebSockets, MySQL triggers (for some logic), Modulith, Session Based authentication
- **Frontend**: React, Vite, TanStack Query, React Easy Crop
- **Database**: NeonDB (PostgreSQL), MySQL

---

## Project Structure
```text
Backend
├── Authentication Module
├── Posting Module
│   ├── Posts
│   ├── Likes
│   └── Bookmarks
├── Connection Module
├── Notification Module
├── Timeline Module
└── Media Module

Frontend
```
---


## 🛠 Setup & Running Instructions

### 1️Start Consul Server
```bash
consul agent -server -bootstrap-expect=1 -data-dir=consul-data -ui -bind=0.0.0.0
```
Access UI at: [http://localhost:8500](http://localhost:8500)

### 2️Start Backend Services
**Order to start:**
1. Config Service
2. Gateway Service
3. Authentication / Posting / Media / Connection / Timeline / Notification services

### 3️Start Frontend
```bash
cd frontend
npm install
npm run dev
```
Now the app will be available locally.

---

## Database Setup
- NeonDB (PostgreSQL) is used instead of local MySQL.
- Update credentials in environment variables before running.

---

## Algorithms & Flows

### 🔹 Timeline Generation
1. Connection service returns a list of followers & followees (given a user id).  
2. Deduplicate into a **set**.  
3. Another endpoint fetches **post IDs** for this set.  
4. Send post IDs to frontend → frontend maps & displays posts.  
5. Show first 10 posts → then "Show more" appends 10 more.  
6. If <10 posts → display global feed.  

### 🔹 Suggested People to Follow
1. Get list of users the logged-in user already follows.  
2. Fetch all users (excluding self).  
3. Compute: `All users – Followed users = Not Followed`.  
4. Display max 4 at a time in frontend (React Query).  
5. Once a user is followed, remove from list & replace with next.  

### 🔹 Settings Page Includes
- Change username  
- Change password  
- Delete account  

🔹 **Change Username**  
- Check backend if new username is already taken.  
- Show ✅ (green tick) if available, ❌ (red cross) if not.  
- Backend endpoint validates & updates.  

---

## Added Features & Fixes
1. Posting  
2. Replying to a post  
3. Liking, bookmarking  
4. Viewing posts in profile & bookmarks  
5. Editing profile  
6. Post modal (pop-up)  
7. Custom photo viewer with close option  
8. Logout  
9. Follow / unfollow system  
10. Delete posts  
11. View parent post  
12. Delete notifications (optional)  
13. All kinds of notifications  
14. Quote retweets (post card & viewer)  
15. Added tabs  
16. Fixed profile header with back button  
17. Make posts clickable properly  
18. User-friendly messages (no bookmarks, no notifications etc.)  
19. Imports fixed (path aliasing + project restructure)  
20. Remove profile photo / background photo option  
21. Quote retweets count & UI fixes  
22. Fixed z-index layering order  
23. Profile photo cropping (React Easy Crop)  
24. Corrected date formatting  
25. Timeline implemented  
26. Fixed edit modal (emoji, giphy close on outside click)  
27. Fixed sidebar user profile container  
28. Added view profile button in sidebar  
29. Fixed likes tab  
30. Added settings page  
31. Added search functionality  
32. Likes are private  
33. Delete account feature  
34. Username validation (no special symbols like `!?/.,><+=` etc.; only letters, digits, emojis, underscore allowed)  
35. Password validation rules  
36. User joined date displayed  
37. Proper formatting for post text  
38. Quote retweets count displayed  

---
## Screenshots

### Home Page
![Home Page](./output/home%20page.png)

### Responsive Profile Page
![Responsive Profile Page](./output/responsive%20profile%20page.png)

### Settings
![Settings](./output/settings.png)

### Likes
- Likes are anonymous  
![Likes are Anonymous](./output/likes%20are%20anonymous.png)

- Likes Tab  
![Likes Tab](./output/likes%20tab.png)

### Notifications
- Follow Notifications  
![Follow Notifications](./output/follow%20notifications.png)

- Mention Notifications  
![Mention Notifications](./output/mention%20notifications.png)

- Following Toast Message  
![Following Toast Message](./output/following%20toast%20message.png)

### Quote Retweet
![Quote Retweeting a Post](./output/quote%20retweeting%20a%20post.png)

---

## License
This project is licensed under **All Rights Reserved**.  
See [LICENSE](./LICENSE) for details.

---

## Author
**Shashank Verma**  
Creator of this Twitter Clone Project.

