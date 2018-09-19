package com.mqfdy.code.springboot.dbs.internal;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener;
import org.eclipse.datatools.connectivity.ProfileManager;

import com.mqfdy.code.springboot.dbs.ConnectionProfile;
import com.mqfdy.code.springboot.dbs.ConnectionProfileListener;
import com.mqfdy.code.springboot.dbs.ConnectionProfileRepository;
import com.mqfdy.code.springboot.utilities.internal.ClassTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.CloneIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.TransformationIterator;


// TODO: Auto-generated Javadoc
/**
 * The Class DTPConnectionProfileRepository.
 *
 * @author mqfdy
 */
public final class DTPConnectionProfileRepository implements
		ConnectionProfileRepository {
	private ProfileManager dtpProfileManager;

	private LocalProfileListener profileListener;

	private final Vector<DTPConnectionProfileWrapper> innerConnectionProfiles = new Vector<DTPConnectionProfileWrapper>();

	// ********** singleton **********

	private static final DTPConnectionProfileRepository INSTANCE = new DTPConnectionProfileRepository();

	/**
	 * Instance.
	 *
	 * @author mqfdy
	 * @return the DTP connection profile repository
	 * @Date 2018-09-03 09:00
	 */
	public static DTPConnectionProfileRepository instance() {
		return INSTANCE;
	}

	/**
	 * 'private' to ensure singleton
	 */
	private DTPConnectionProfileRepository() {
		super();
	}

	// ********** lifecycle **********

	/**
	 * called by plug-in
	 */
	public synchronized void start() {
		this.dtpProfileManager = ProfileManager.getInstance();
		this.profileListener = new LocalProfileListener();
		this.dtpProfileManager.addProfileListener(this.profileListener);
		for (IConnectionProfile dtpProfile : this.dtpProfileManager
				.getProfiles()) {
			this.innerConnectionProfiles.add(new DTPConnectionProfileWrapper(
					dtpProfile));
		}
	}

	/**
	 * called by plug-in
	 */
	public synchronized void stop() {
		for (DTPConnectionProfileWrapper profile : this.innerConnectionProfiles) {
			profile.dispose();
		}
		this.innerConnectionProfiles.clear();
		this.dtpProfileManager.removeProfileListener(this.profileListener);
		this.profileListener = null;
		this.dtpProfileManager = null;
	}

	// ********** profiles **********

	public synchronized Iterator<ConnectionProfile> connectionProfiles() {
		return new CloneIterator<ConnectionProfile>(this.innerConnectionProfiles); // read
		// -
		// only
	}

	private synchronized Iterator<DTPConnectionProfileWrapper> connectionProfileWrappers() {
		return new CloneIterator<DTPConnectionProfileWrapper>(
				this.innerConnectionProfiles); // read-only
	}

	public int connectionProfilesSize() {
		return this.innerConnectionProfiles.size();
	}

	public Iterator<String> connectionProfileNames() {
		return new TransformationIterator<DTPConnectionProfileWrapper, String>(
				this.connectionProfileWrappers()) {
			@Override
			protected String transform(DTPConnectionProfileWrapper profile) {
				return profile.getName();
			}
		};
	}

	public boolean containsConnectionProfileNamed(String name) {
		return !this.connectionProfileNamed(name).isNull();
	}

	public ConnectionProfile connectionProfileNamed(String name) {
		for (Iterator<DTPConnectionProfileWrapper> stream = this
				.connectionProfileWrappers(); stream.hasNext();) {
			DTPConnectionProfileWrapper profile = stream.next();
			if (profile.getName().equals(name)) {
				return profile;
			}
		}
		return NullConnectionProfile.instance();
	}

	synchronized DTPConnectionProfileWrapper addConnectionProfile(
			IConnectionProfile dtpConnectionProfile) {
		for (DTPConnectionProfileWrapper wrapper : this.innerConnectionProfiles) {
			if (wrapper.wraps(dtpConnectionProfile)) {
				throw new IllegalStateException(
						"duplicate connection profile: " + dtpConnectionProfile.getName()); //$NON-NLS-1$
			}
		}
		DTPConnectionProfileWrapper wrapper = new DTPConnectionProfileWrapper(
				dtpConnectionProfile);
		this.innerConnectionProfiles.add(wrapper);
		return wrapper;
	}

	synchronized DTPConnectionProfileWrapper removeConnectionProfile(
			IConnectionProfile dtpConnectionProfile) {
		for (Iterator<DTPConnectionProfileWrapper> stream = this.innerConnectionProfiles
				.iterator(); stream.hasNext();) {
			DTPConnectionProfileWrapper wrapper = stream.next();
			if (wrapper.wraps(dtpConnectionProfile)) {
				stream.remove();
				return wrapper;
			}
		}
		throw new IllegalStateException(
				"invalid connection profile: " + dtpConnectionProfile.getName()); //$NON-NLS-1$
	}

	synchronized DTPConnectionProfileWrapper connectionProfile(
			IConnectionProfile dtpConnectionProfile) {
		for (DTPConnectionProfileWrapper wrapper : this.innerConnectionProfiles) {
			if (wrapper.wraps(dtpConnectionProfile)) {
				return wrapper;
			}
		}
		throw new IllegalStateException(
				"invalid connection profile: " + dtpConnectionProfile.getName()); //$NON-NLS-1$
	}

	// ********** Object overrides **********

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.toStringClassNameForObject(this));
		sb.append((this.innerConnectionProfiles != null) ? this.innerConnectionProfiles
				: "<'connectionProfiles' uninitialized>"); //$NON-NLS-1$
		return sb.toString();
	}

	// ********** listeners **********

	public void addConnectionProfileListener(ConnectionProfileListener listener) {
		this.profileListener.addConnectionProfileListener(listener);
	}

	public void removeConnectionProfileListener(
			ConnectionProfileListener listener) {
		this.profileListener.removeConnectionProfileListener(listener);
	}

	// ********** listener **********

	/**
	 * Keep the repository in synch with the DTP profile manager and forward
	 * events to the repository's listeners.
	 */
	private class LocalProfileListener implements IProfileListener {
		private Vector<ConnectionProfileListener> lListeners = 
			new Vector<ConnectionProfileListener>();

		LocalProfileListener() {
			super();
		}

		void addConnectionProfileListener(ConnectionProfileListener listener) {
			this.lListeners.add(listener);
		}

		void removeConnectionProfileListener(ConnectionProfileListener listener) {
			this.lListeners.remove(listener);
		}

		private Iterator<ConnectionProfileListener> listeners() {
			return new CloneIterator<ConnectionProfileListener>(this.lListeners);
		}

		// ********** IProfileListener implementation **********

		public void profileAdded(IConnectionProfile dtpProfile) {
			// synch the repository then notify listeners
			DTPConnectionProfileWrapper profile = DTPConnectionProfileRepository.this
					.addConnectionProfile(dtpProfile);
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream
					.hasNext();) {
				stream.next().connectionProfileReplaced(
						NullConnectionProfile.instance(), profile);
			}
		}

		public void profileChanged(IConnectionProfile dtpProfile) {
			DTPConnectionProfileWrapper profile = DTPConnectionProfileRepository.this
					.connectionProfile(dtpProfile);
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream
					.hasNext();) {
				stream.next().connectionProfileChanged(profile);
			}
		}

		public void profileDeleted(IConnectionProfile dtpProfile) {
			// synch the repository then notify listeners
			DTPConnectionProfileWrapper profile = DTPConnectionProfileRepository.this
					.removeConnectionProfile(dtpProfile);
			for (Iterator<ConnectionProfileListener> stream = this.listeners(); stream
					.hasNext();) {
				stream.next().connectionProfileReplaced(profile,
						NullConnectionProfile.instance());
			}
		}

	}

}
