package verifier.util

import core.utils.files.fileSep
import core.utils.preferences.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.ac.ox.cs.fdr.Session
import uk.ac.ox.cs.fdr.fdr

object FdrLoader {
    var fdrLoaded = false
        private set

    suspend fun loadFdr(): Session {
        if (fdrLoaded) return getSession()

        return loadLibrary(Preferences.fdrPath).also {
            fdrLoaded = true
        }
    }

    /**
     * Throws an exception if [path] is not a valid FDR path.
     */
    suspend fun checkFdrPath(path: String) {
        loadLibrary(path)
        fdr.libraryExit()
    }

    private fun getSession() = Session()

    private suspend fun loadLibrary(fdrPath: String): Session = withContext(Dispatchers.IO) {
        val libPath = "$fdrPath${fileSep}bin"

        try {
            System.load("$libPath${fileSep}libfdr_java.dll")

            return@withContext getSession()
        } catch (e: Throwable) {
            if (e !is UnsatisfiedLinkError || e.message?.contains("dependent") != true) {
                throw FdrNotFoundException()
            } else {
                try {
                    return@withContext loadLibraryAndDependencies(libPath)
                } catch (e: Throwable) {
                    throw InvalidFdrException()
                }
            }
        }

    }

    // TODO(platform): file extensions
    private fun loadLibraryAndDependencies(libPath: String): Session {
        /*        */System.load("$libPath${fileSep}libwinpthread-1.dll")
        /*    */System.load("$libPath${fileSep}libgcc_s_seh-1.dll")
        /*    */System.load("$libPath${fileSep}libstdc++-6.dll")
        /*            */System.load("$libPath${fileSep}libboost_program_options-mgw53-mt-1_60.dll")
        /*                    */System.load("$libPath${fileSep}libboost_program_options-mgw53-mt-1_60.dll")
        /*                    */System.load("$libPath${fileSep}libboost_serialization-mgw53-mt-1_60.dll")
        /*                    */System.load("$libPath${fileSep}libboost_iostreams-mgw53-mt-1_60.dll")
        /*                        */System.load("$libPath${fileSep}libboost_system-mgw53-mt-1_60.dll")
        /*                    */System.load("$libPath${fileSep}libboost_filesystem-mgw53-mt-1_60.dll")
        /*                        */System.load("$libPath${fileSep}libssp-0.dll")
        /*                        */System.load("$libPath${fileSep}libboost_date_time-mgw53-mt-1_60.dll")
        /*                        */System.load("$libPath${fileSep}librefines.dll")
        /*                    */System.load("$libPath${fileSep}librefines_licensing.dll")
        /*                */System.load("$libPath${fileSep}libprocess_compiler.dll")
        /*            */System.load("$libPath${fileSep}libcsp_operators.dll")
        /*        */System.load("$libPath${fileSep}libcspm_process_compiler.dll")
        /*    */System.load("$libPath${fileSep}libfdr.dll")
        System.load("$libPath${fileSep}libfdr_java.dll")

        return getSession()
    }
}