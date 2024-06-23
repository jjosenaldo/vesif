package verifier.util

import core.utils.files.fileSep
import core.utils.preferences.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.ac.ox.cs.fdr.Session

object FdrLoader {
    var fdrLoaded = false
        private set

    suspend fun loadFdr(): Session {
        if (fdrLoaded) return getSession()

        return loadLibrary().also {
            fdrLoaded = true
        }
    }

    private fun getSession() = Session()

    private suspend fun loadLibrary(): Session = withContext(Dispatchers.IO) {
        val fdrPath = Preferences.fdrPath

        try {
            System.load("$fdrPath${fileSep}libfdr_java.dll")

            return@withContext getSession()
        } catch (e: Throwable) {
            if (e !is UnsatisfiedLinkError || e.message?.contains("dependent") != true) {
                throw FdrNotFoundException()
            } else {
                try {
                    return@withContext loadLibraryAndDependencies()
                } catch (e: Throwable) {
                    throw InvalidFdrException()
                }
            }
        }

    }

    // TODO(platform): file extensions
    private fun loadLibraryAndDependencies(): Session {
        val fdrPath = Preferences.fdrPath
        /*        */System.load("$fdrPath${fileSep}libwinpthread-1.dll")
        /*    */System.load("$fdrPath${fileSep}libgcc_s_seh-1.dll")
        /*    */System.load("$fdrPath${fileSep}libstdc++-6.dll")
        /*            */System.load("$fdrPath${fileSep}libboost_program_options-mgw53-mt-1_60.dll")
        /*                    */System.load("$fdrPath${fileSep}libboost_program_options-mgw53-mt-1_60.dll")
        /*                    */System.load("$fdrPath${fileSep}libboost_serialization-mgw53-mt-1_60.dll")
        /*                    */System.load("$fdrPath${fileSep}libboost_iostreams-mgw53-mt-1_60.dll")
        /*                        */System.load("$fdrPath${fileSep}libboost_system-mgw53-mt-1_60.dll")
        /*                    */System.load("$fdrPath${fileSep}libboost_filesystem-mgw53-mt-1_60.dll")
        /*                        */System.load("$fdrPath${fileSep}libssp-0.dll")
        /*                        */System.load("$fdrPath${fileSep}libboost_date_time-mgw53-mt-1_60.dll")
        /*                        */System.load("$fdrPath${fileSep}librefines.dll")
        /*                    */System.load("$fdrPath${fileSep}librefines_licensing.dll")
        /*                */System.load("$fdrPath${fileSep}libprocess_compiler.dll")
        /*            */System.load("$fdrPath${fileSep}libcsp_operators.dll")
        /*        */System.load("$fdrPath${fileSep}libcspm_process_compiler.dll")
        /*    */System.load("$fdrPath${fileSep}libfdr.dll")
        System.load("$fdrPath${fileSep}libfdr_java.dll")

        return getSession()
    }
}