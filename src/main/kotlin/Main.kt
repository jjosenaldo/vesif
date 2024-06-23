import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext.startKoin
import ui.App
import ui.di.appModule
import java.io.File


fun main() = application {
    startKoin {
        modules(appModule())
    }

    println(System.getProperty("java.library.path"))

    /*        */System.loadLibrary("libwinpthread-1")
    /*    */System.loadLibrary("libgcc_s_seh-1")
    /*    */System.loadLibrary("libstdc++-6")
    /*            */System.loadLibrary("libboost_program_options-mgw53-mt-1_60")
    /*                    */System.loadLibrary("libboost_program_options-mgw53-mt-1_60")
    /*                    */System.loadLibrary("libboost_serialization-mgw53-mt-1_60")
    /*                    */System.loadLibrary("libboost_iostreams-mgw53-mt-1_60")
    /*                        */System.loadLibrary("libboost_system-mgw53-mt-1_60")
    /*                    */System.loadLibrary("libboost_filesystem-mgw53-mt-1_60")
    /*                        */System.loadLibrary("libssp-0")
    /*                        */System.loadLibrary("libboost_date_time-mgw53-mt-1_60")
    /*                        */System.loadLibrary("librefines")
    /*                    */System.loadLibrary("librefines_licensing")
    /*                */System.loadLibrary("libprocess_compiler")
    /*            */System.loadLibrary("libcsp_operators")
    /*        */System.loadLibrary("libcspm_process_compiler")
    /*    */System.loadLibrary("libfdr")
    System.loadLibrary("libfdr_java")


    App()
}

//fun main(): Unit = runBlocking {
//    AssertionManager(CspGenerator()).runAssertionsReturnFailing(Circuit.DEFAULT, AssertionType.entries)
//}