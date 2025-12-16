# Sandro021-TBC-IT-Academy-android
TBC it academy homeworks
This is gym progress tracker application which allows user to note their progress and store them safely.
It can hold years of data and it is connected to user email.
This project was built with MVI pattern, using clean architecture principles as possible.
User can register and add whatever exercise wants and note their progress. User can easily see previous workouts to find 
out if he/she has progress or not.I used Firebase for login and registration and Firestore to store data. Used recyclers
to show workout groups, horizontall calendar, FragmentResult API to send choosen workout from one fragment to another.
Main part of project is that I seed data to firestore and every new user has default exercises group which includes every 
muscle and user dont need to add anything.

ეს არის აპლიკაცია რომლის საშუალებითაც მომხმარებელს შეუძლია თავისი პროგრესი მოინიშნოს და შეინახოს ისე რომ არ დაეკარგოს.
მონაცემები მიბმულია ემილზე და პაროლზე,ამიტომაც თუ მოწყობილობას შეცლის შეეძლება პროგრესი გადაიტანოს მარტივად აპლიკაციაში 
ავტორიზაციის შემდეგ. 
პროექტი შეძლებისდაგვარად სუფთა არქიტექტურითაა აწყობილი MVI პატერნით. Firebase ის და Firestore ის დახმარებით ინახება მონაცემები.
მომხმარებელს შეუძლია დაამატოს სასურველი ვარჯიში შესაბამის კუნთის კატეგორიაში. კუნთის კატეგორია გაწერილია ყველა მომხმარებლისთვის
და არ არის ზედმეტი კატეგორიის დამატება საჭირო. ეს აპლიკაციის გამოყენებას ამარტივებს და კომფორტულს ხდის.
