# Refleksi 1

Dalam pengimplementasian dua fitur baru menggunakan Spring Boot, saya telah menerapkan prinsip dari Clean Code yang mencakup hal-hal berikut,

**Penamaan Variable**  
Dalam penamaan variabel, saya menggunakan nama-nama yang jelas untuk memberikan konteks yang cukup tentang variables tersebut digunakan untuk apa, sehingga nama variablenya tidak bertele-tele tetapi juga tidak terlalu singkat.

**Memisahkan Controller, Service, dan Repository**  
Setiap bagian dari kode mencakup berbagai peran yang berbeda yang akan memudahkan apabila adanya kesalahan dan butuh perbaikan. Hal ini dilakukan dengan memisahkan action dalam produk menjadi ProductController, ProductService, dan juga Product Repository dengan peranannya masing masing.

**Penggunaan @Getter dan @Setter**  
yang memudahkan dan mensimplifikasi dalam penerapannya, sehingga tidak perlu kode yang terlalu panjang dan juga tidak readable jika dilakukan ke dalam banyak aspek.

Selain itu, saya juga telah mengimplementasikan Secure Coding di dalam penerapan ini, yaitu dengan mengubah ID yang tadinya hanya berupa Sting menjadi berbasis UUID yang digenerate secara random sehingga sifatnya unpredictable dan mengurangi resiko perubahan oleh peretas apabila hanya berbasis integer.  Namun, pengimplementasian create Product yang diberikan di tutorial masih tidak menggunakan input validasi yang berpotensi dapat diexploit oleh peretas atau pengguna yang tidak tahu format inputnya. Oleh karena itu, akan lebih baik jika input divalidasi sebelum diproses di dalam program karena input yang salah dapat berpotensi membuat program menjadi error dan juga crash.

***

# Refleksi 2

Setelah melakukan dan membuat unit test, saya merasa lebih ter-assure dengan apakah program saya berhasil bekerja dengan baik atau tidak. Hal ini juga dapat menguji proses development ke depannya karena ketika adanya perubahan yang merusak logic sebelumnya, testnya akan menunjukan kalau itu gagal dan harus saya perbaiki.

Saya juga mengetahui pemahaman baru bahwa selama ini ada 3 jenis testcase yaitu positive case, negative case, dan juga edge case. Positive case mengecek jika produknya valid. negative case mengecek jika datanya invalid, dan edge case mengecek jika suatu hal kosong atau tidak ada.

Ketika berbicara tentang code coverage, saya teringat dengan unit testing di mata kuliah DDP yang membutuhkan coverage di atas 90%, saya sadar bahwa semakin banyak coverage berarti semakin banyak porsi dari line code yang telah dicek. Namun,  100% coverage juga tidak berarti suatu kode benar-benar memiliki logic yang tepat. Hal ini dikarenakan ketika kita memiliki coverage penuh, bisa saja kita mengecek input yang salah tetapi juga tidak mencover situasi di mana itu seharusnya benar. Selain itu, jika seseorang benar-benar lupa mengimplementasikan suatu function di suatu waktu, dan coverage testnya akan tetap 100% meskipun implementasi dari function tersebut yang berupa bug tidak ada.

Jika kita membuat test baru, seperti COUNTProductFunctionalTest.java dan menggunakan requirements dari CreateProductFuctionalTest.java, akan adanya duplikasi code yang terjadi. Hal ini menyebabkan jika setupnya diganti, perbaikan yang harus dilakukan harus dilakukan 2 kali atau bahkan lebih jika ada lebih banyak test yang diterapkan dengan sistem yang sama. Readability dari testnya juga akan berkurang karena orang akan teroverwhelmed terlebih dahulu dengan apa yang ditampilkan di awal dengan requirements yang ada.

Improvement dapat dilakukan dengan membuat Base Test Class yang menghandle setupnya, setelah itu setiap test lain yang membutuhkan resource yang sama bisa mengextend Base tersebut sehingga setiap filenya hanya memiliki test logicnya dan bukan sesuatu yang repetitif.

***
# Refleksi Modul 2

1. Selama melakukan exercise, saya menemukan dan telah memperbaiki UnnecessaryModifier code warning yang ditunjukan oleh code scanner PMD.  
   Hal ini terjadi di ProductService.java, methods yang saya implemen di dalam interface java sudah secara otomatis dalam bentuk public dan abstract, pelabelan dengan abstract lagi menjadi redundan.  
   Oleh karena itu, saya melakukan perbaikan dengan menghilangkan kata-kata public dari methods yang ada, sehingga clean code dapat diterapkan dan penggunaan kode yang redundan berhsil dikurangi.
2. Ya, implementasi sekarang telah berhasil memenuhi definisi dari Continous Integration dan juga Deployment.  
   Di bagian Continous Integration, beberapa workflow seperti ci.yml, pmd.yml, dan scorecard.yml telah berhasil diterapkan dan secara otomatis dijalankan setiap adanya pull dan push request.  
   Fungsi dari workflow tersebut adalah untuk mengecek code yang ada, merun unit test yang sudah dibuat, dan memverifikasi unit test tersebut dengan JaCoCo, juga menerapkan analisis keamanan dan kualitas.  
   Sleain itu, untuk CD atau Continous Deployment, ada deploy.yml yang secara otomatis dijalankan setiap terjadi push di branch, workflow tersebut akan mengeksekusi langkah-langkah untuk membuild aplikasi dan mendeploynya ke Koyeb.  
   Langkah ini membuat codenya akan selalu running tanpa membutuhkan andil dari manusia dan memenuhi CD.

***
# Modul 3

1. Single Responsibility Principle
   Di kode hasil modifikasi, saya sudah menerapkan SRP dengan memodifikasi ProductController yang tadinya memiliki dua class berbeda, ProductController dan CarController.
   Saya memisahkan kedua file dengan membuatkan file baru untuk CarController, sehingga setiap controller class memiliki tanggung jawab menghandle HTTP request untuk product maupun car.
2. Open Closed Principle
   Di kode awal, ProductServiceImpl dan CarServiceImpl bergantung kepada concrete class ProductRepository dan CarRepository.
   Ketika nanti ada perubahan, harus ada perubahan di setiap file yang membuatnya sulit untuk diubah. Oleh karena itu, saya mengubah ProductRepository dan CarRepository menjadi interfaces.
   Kemudian, dibuatkan implementasinya berupa ProductRepositoryImpl dan CarRepositoryImpl untuk menghandle bentuk data saatini.
3. Liskov Substitution Principle
   CarController yang awalnya mengextend ProductController tidak bisa mensubstitusi parentnya, selain itu dengan mengextend, ada endpoints yang juga ikut terbawa seperti /product/create padahal yang kita gunakan saat ini adalah untuk Cars.
   Maka dari itu, saya menghilangkan relationship tersebut dan membuatnya menjadi sebuah class sendiri.
4. Interface Segregation Principle
   Kode awal sebelum diubah sudah menerapkan CarService dan productService.
   Namun beberapa nama yang diubah berbeda, sehingga saya melakuakn standarisasi supaya meskipun sudah di segregate, nama-nama methodnya distandarisasi supaya mudah diimplemen.
5. Dependency Inversion Principle
   CarController sebelumnya bergantung pada CarServiceImpl yang merupakan concrete class.
   Saya mengubahnya menjadi bergantung pada abstraction berupa CarService, sehingga DIP sudah diterapkan.

### Manfaat SOLID
Dengan menerapkan SOLID principle, program bisa lebih terbuka terhadap perubahan dan dimengerti oleh coder lain.
Misalnya, dengan menerapkan OCP, aplikasi yang dicode akan lebih mudah beradaptasi dengan kebutuhan baru dan lebih sedikit code yang harus diubah.
Selain itu, proses pembetulan bug (debugging) juga akan lebih mudah dilakukan karena diterapkan SRP yang akan mengisolasi bug sehingga mudah dicari code mana yang harus diperbaiki.
Penamaan yang konsisten dengan interface juga membuat jika kita bekerja sama dengan programmer lain ataupun diri sendiri lebih mudah karena penamaan yang konsisten.

### Apa yang terjadi jika tidak menerapkan?
Yang terjadi jika tidak menerapkan SOLID adalah kebalikan dari apa yang sudah dijelaskan di atas.
Ketika terjadi error, kita akan susah menemukan penyebabnya karena banyak ketergantungan.
Di sisi lain, ketika kita ingin mengubah tujuan atau scale dari code kita, kita akan sulit juga melakukannya karena banyak code yang harus diubah dan tidak mudah disesuaikan.
Di sisi testing juga, ketika kita punya satu function yang sangat besar instead of function yang kecil, atau dependency ke concrete class, ketika dependency itu kita ubah, maka dependeenya juga akan terpengaruh.
Kita menjadi harus membuat strukturnya dari awal dan tidak bisa membuat struktur pengganti baru dengan mudah.

***
# Refleksi Modul 4

1. Evaluasi TDD Flow berdasarkan Objektif Percival (2017)

Dalam menerapkan workflow *Test-Driven Development* (TDD) pada latihan modul ini, jika dievaluasi melalui tiga pertanyaan reflektif dari Percival:
* **Correctness:** Menulis tes sebelum melakukan implementasi (fase RED) benar-benar membantu saya memastikan bahwa semua *edge case* dan kebutuhan fungsional (seperti batasan status Enum pada `Order`) telah dipertimbangkan sejak awal. Saya merasa lebih yakin bahwa aplikasi berfungsi sesuai ekspektasi.
* **Maintainability:** Adanya tes yang sudah tertulis memberikan saya rasa aman saat harus melakukan tahap *refactoring*. Sebagai contoh, saat mengimplementasikan `OrderStatus` enum untuk menggantikan tipe *hardcoded string*, saya tidak perlu khawatir merusak fitur yang sudah ada karena unit test akan langsung mendeteksi jika ada bagian logic yang rusak.
* **Productive Workflow:** Walaupun pada awalnya terasa lambat karena saya harus memikirkan kasus pengujian terlebih dahulu, feedback cycle yang sangat cepat dari eksekusi tes otomatis membuat proses *debugging* menjadi jauh lebih efisien.

2. Evaluasi Unit Test berdasarkan Prinsip F.I.R.S.T.

Secara keseluruhan, unit test yang telah saya buat di Tutorial ini sudah berhasil memenuhi prinsip F.I.R.S.T.:
* **Fast:** Pengujian berjalan dengan sangat cepat dan memberikan *feedback* instan tanpa menghambat alur kerja karena hanya berfokus pada unit logic yang spesifik.
* **Isolated/Independent:** Setiap metode pengujian berdiri sendiri secara independen. Saya telah menggunakan `@BeforeEach` untuk mempersiapkan data awal (seperti inisialisasi list `products` dan pembuatan objek `Order`) sehingga satu kasus pengujian tidak memengaruhi *state* pengujian lainnya.
* **Repeatable:** Karena pengujian diisolasi dengan baik, tes memberikan hasil yang konsisten setiap kali dijalankan berulang-ulang tanpa dipengaruhi oleh faktor *environment* eksternal.
* **Self-Validating:** Setiap *test case* menggunakan assertion yang tegas (seperti `assertEquals` atau `assertThrows`), sehingga hasil lolos/gagal dapat langsung dinilai oleh mesin secara otomatis tanpa memerlukan inspeksi manual pada *print console*.
* **Thorough/Timely:** Pengujian dibuat tepat waktu sebelum kode diproduksi (sesuai hukum TDD). Selain itu, skenario tes dibuat secara menyeluruh untuk mencakup *happy path* (jalur normal) maupun *unhappy path* (kasus kegagalan/batas), contohnya menguji penolakan program jika list produk kosong atau format status tidak valid.