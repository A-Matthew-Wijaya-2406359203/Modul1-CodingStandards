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