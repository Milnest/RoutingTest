package com.milnest.testapp.presentation.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.ActivityCompat
import android.util.Size
import android.view.*
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.fragment_camera.*
import java.util.*

class CameraFragment : BaseFragment(), CameraView {
    private val REQUEST_CAMERA_PERMISSION = 200

    override fun getFragType(): FragType {
        return FragType.CAMERA
    }

    override fun setUpActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var cameraManager: CameraManager? = null
    var cameraId: String? = null
    var cameraCaptureSessions: CameraCaptureSession? = null
    var captureRequestBuilder: CaptureRequest.Builder? = null
    var cameraDevice: CameraDevice? = null
    var imageDimension: Size? = null

    @InjectPresenter
    lateinit var presenter: CameraPresenter

    var backgroundThread: HandlerThread? = null
    var  backgroundHandler: Handler? = null

    /**
     * [CaptureRequest.Builder] for the camera preview
     */
    private lateinit var previewRequestBuilder: CaptureRequest.Builder

    /**
     * [CaptureRequest] generated by [.previewRequestBuilder]
     */
    private lateinit var previewRequest: CaptureRequest

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (textureView.isAvailable)
            openCamera()
        else
            textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture?, p1: Int, p2: Int) {
                }

                override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture?) {
                }

                override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture?): Boolean {
                    return false
                }

                override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture?, p1: Int, p2: Int) {
                    openCamera()
                }

            }
    }

    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("Camera BG")
        backgroundThread?.start()
        backgroundHandler = Handler(backgroundThread?.looper)
    }

    private fun openCamera() {
        val manager = context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = manager.cameraIdList[0]
            for (camId in manager.cameraIdList){
                if (manager.getCameraCharacteristics(camId).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT)
                    cameraId = camId
            }
            val characteristics = manager.getCameraCharacteristics(cameraId)
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
            imageDimension = map.getOutputSizes(SurfaceTexture::class.java)[0]
            //Check realtime permission if run higher API 23
            if (ActivityCompat.checkSelfPermission(this@CameraFragment.context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@CameraFragment.activity!!, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
                return
            }
            manager.openCamera(cameraId, stateCallback, null)

        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    var stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(cameraDevice: CameraDevice) {
            cameraDevice.close()
        }

        override fun onError(cameraDevice: CameraDevice, i: Int) {
            cameraDevice.close()
            this@CameraFragment.cameraDevice = null
        }
    }

    private fun createCameraPreview() {
        try {
            val texture = textureView.surfaceTexture!!
            texture.setDefaultBufferSize(imageDimension!!.getWidth(), imageDimension!!.getHeight())
            val surface = Surface(texture)
            captureRequestBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder?.addTarget(surface)
            cameraDevice?.createCaptureSession(Arrays.asList(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                    if (cameraDevice == null)
                        return
                    cameraCaptureSessions = cameraCaptureSession
                    updatePreview()
                }

                override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                    Toast.makeText(this@CameraFragment.context, "Changed", Toast.LENGTH_SHORT).show()
                }
            }, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    private fun updatePreview() {
        if (cameraDevice == null)
            Toast.makeText(this@CameraFragment.context, "Error", Toast.LENGTH_SHORT).show()
        captureRequestBuilder?.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSessions?.setRepeatingRequest(captureRequestBuilder?.build(), null, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    override fun onPause() {
        super.onPause()
        stopBackgroundThread()
    }

    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            backgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}
