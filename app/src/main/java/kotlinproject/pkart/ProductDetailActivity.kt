package kotlinproject.pkart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinproject.pkart.databinding.ActivityProductDetailBinding
import kotlinproject.pkart.roomdb.AppDatabase
import kotlinproject.pkart.roomdb.ProductDao
import kotlinproject.pkart.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProductDetails(intent.getStringExtra("id"))
    }

    private fun getProductDetails(proId: String?) {

        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name = it.getString("productName")
                val productSp = it.getString("productSp")
                val productDesc = it.getString("productDescription")

                binding.textView7.text = name
                binding.textView8.text = productSp
                binding.textView9.text = productDesc


                val slideList = ArrayList<SlideModel>()
                for (data in list) {
                    slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                }

                cartAction(proId, name, productSp, it.getString("productCoverImg"))


                binding.imageSlider.setImageList(slideList)
            }

            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cartAction(proId: String, name: String?, productSp: String?, coverimg: String?) {

        val productDao = AppDatabase.getInstance(this).productDao()

        if (productDao.isExit(proId) != null) {
            binding.textView10.text = "Go to cart"
        } else {
            binding.textView10.text = "Add to Cart"
        }

        binding.textView10.setOnClickListener {
            if (productDao.isExit(proId) != null) {
                openCart()
            } else {
                addtoCart(productDao, proId, name, productSp, coverimg)
            }
        }
    }

    private fun addtoCart(
        productDao: ProductDao,
        proId: String,
        name: String?,
        productSp: String?,
        coverimg: String?
        ) {
        val data = ProductModel(proId, name, coverimg, productSp)

        lifecycleScope.launch(Dispatchers.IO) {
            productDao.insertProduct(data)
            binding.textView10.text = "Go to Cart"
        }

    }

    private fun openCart() {
        val preferences = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
//2:39
